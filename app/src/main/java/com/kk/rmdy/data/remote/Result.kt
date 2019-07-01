package com.kk.rmdy.data.remote

import com.kk.rmdy.data.remote.error.BaseError

/**
 * Created by Kirill Kartukov on 05.11.2018.
 */
sealed class Result<out T> {

    class Success<out T>(val data: T) : Result<T>()

    class Error<out T>(val networkError: BaseError) : Result<T>()

}