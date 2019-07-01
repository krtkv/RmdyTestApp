package com.kk.rmdy.data.remote

import com.kk.rmdy.data.remote.response.login.MemberLoginResponse
import com.kk.rmdy.data.remote.response.trackersteps.MemberTrackerStepsResponse

/**
 * Created by Kirill Kartukov on 27.06.2019.
 */
interface NetworkRepository {

    suspend fun memberLogin(userName: String, userPass: String): Result<MemberLoginResponse>

    suspend fun memberTrackerSteps(range: String): Result<MemberTrackerStepsResponse>

}