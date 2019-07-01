package com.kk.rmdy.data.remote.request

import com.google.gson.annotations.SerializedName

/**
 * Created by Kirill Kartukov on 27.06.2019.
 */
data class MemberLoginRequest(
    @SerializedName("UserName") private val userName: String,
    @SerializedName("Password") private val userPass: String,
    @SerializedName("TenantID") val tenantId: Int = 1
)