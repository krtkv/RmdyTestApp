package com.kk.rmdy.data.remote.error

/**
 * Created by Kirill Kartukov on 06.11.2018.
 */
class ServerError(val code: Int, val descriptionMessage: String?) : BaseError()