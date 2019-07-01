package com.kk.rmdy.data.remote.response.login

import com.google.gson.annotations.SerializedName

/**
 * Created by Kirill Kartukov on 27.06.2019.
 */
data class MemberLoginResponse(
    @SerializedName("Tokens") val token: Token? = null
)