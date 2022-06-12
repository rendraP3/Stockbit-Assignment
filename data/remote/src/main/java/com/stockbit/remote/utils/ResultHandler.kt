package com.stockbit.remote.utils

import com.stockbit.model.Result
import retrofit2.Response
import java.lang.Exception

suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> = try {
    val response = call()

    if (response.isSuccessful) response.body()?.run {
        Result.success(this)
    } ?: Result.error(response.message(), null, response.code().toString())
    else Result.error(response.message(), null, response.code().toString())
} catch (e: Exception) {
    Result.error(e.message.orEmpty(), null, "0")
}