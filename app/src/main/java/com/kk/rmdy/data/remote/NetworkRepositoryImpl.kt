package com.kk.rmdy.data.remote

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.kk.rmdy.data.local.PreferenceRepository
import com.kk.rmdy.data.remote.error.BaseError
import com.kk.rmdy.data.remote.error.NoConnectivityError
import com.kk.rmdy.data.remote.error.ServerError
import com.kk.rmdy.data.remote.request.MemberLoginRequest
import com.kk.rmdy.data.remote.response.login.MemberLoginResponse
import com.kk.rmdy.data.remote.response.trackersteps.MemberTrackerStepsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Created by Kirill Kartukov on 27.06.2019.
 */
class NetworkRepositoryImpl(
    private val context: Context,
    private val prefs: PreferenceRepository,
    private val service: MtdApiService,
    private val gson: Gson
) : NetworkRepository {

    override suspend fun memberLogin(userName: String, userPass: String): Result<MemberLoginResponse> {
        return withContext(Dispatchers.IO) {
            getResult(
                service.memberLogin(MemberLoginRequest(userName, userPass)),
                MemberLoginResponse::class.java
            )
        }
    }

    override suspend fun memberTrackerSteps(range: String): Result<MemberTrackerStepsResponse> {
        return withContext(Dispatchers.IO) {
            getResult(
                service.memberTrackerSteps(prefs.getAuthToken(), prefs.getSessionToken(), range),
                MemberTrackerStepsResponse::class.java
            )
        }
    }

    private suspend fun <T> getResult(
        call: Call<ResponseBody>,
        outType: Class<T>
    ): Result<T> =
        suspendCoroutine {
            call.enqueue(object : Callback<ResponseBody> {

                override fun onFailure(call: Call<ResponseBody>?, error: Throwable?) {
                    return if (!hasNetwork())
                        it.resume(Result.Error(NoConnectivityError()))
                    else
                        it.resume(Result.Error(BaseError()))
                }

                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                    response?.let { resp ->
                        if (response.isSuccessful) {
                            resp.body()?.let { body ->
                                val responseBody = body.string()
                                return try {
                                    it.resume((Result.Success(gson.fromJson(responseBody, outType))))
                                } catch (e: JsonSyntaxException) {
                                    e.printStackTrace()
                                    it.resume(Result.Error(BaseError()))
                                }
                            } ?: run {
                                it.resume(Result.Error(BaseError()))
                            }
                        } else {
                            it.resume(Result.Error(ServerError(resp.code(), resp.message())))
                        }
                    } ?: run {
                        it.resume(Result.Error(BaseError()))
                    }

                }
            })
        }

    private fun hasNetwork(): Boolean {
        return (context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager).activeNetworkInfo?.isConnected ?: false
    }

}