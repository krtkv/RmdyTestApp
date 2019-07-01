package com.kk.rmdy.data.remote

import com.kk.rmdy.data.remote.request.MemberLoginRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Kirill Kartukov on 27.06.2019.
 */
interface MtdApiService {

    @POST("Account/Login")
    fun memberLogin(@Body payload: MemberLoginRequest): Call<ResponseBody>

    @GET("Trackers/Steps/WithGoal/{range}")
    fun memberTrackerSteps(
        @Header("AuthToken") authToken: String,
        @Header("SessionToken") sessionToken: String,
        @Path(value = "range", encoded = true) range: String
    ): Call<ResponseBody>

}