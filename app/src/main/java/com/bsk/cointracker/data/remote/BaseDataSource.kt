package com.bsk.cointracker.data.remote

import com.bsk.cointracker.data.remote.common.ApiResult
import retrofit2.Response
import timber.log.Timber


abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ApiResult<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return ApiResult.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): ApiResult<T> {
        Timber.e(message)
        return ApiResult.error("Network call has failed for a following reason: $message")
    }
}

