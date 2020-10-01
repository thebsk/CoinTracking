package com.bsk.cointracker.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.bsk.cointracker.data.remote.common.ApiResult
import com.bsk.cointracker.data.remote.common.ApiResult.Status.ERROR
import com.bsk.cointracker.data.remote.common.ApiResult.Status.SUCCESS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


fun <T, A> resultLiveData(
    scope: CoroutineScope,
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> ApiResult<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<ApiResult<T>> =
    liveData(scope.coroutineContext) {
        emit(ApiResult.loading())

        withContext(Dispatchers.IO) {
            val source = databaseQuery.invoke().map { ApiResult.success(it) }
            emitSource(source)

            val responseStatus = networkCall.invoke()
            if (responseStatus.status == SUCCESS) {
                saveCallResult(responseStatus.data!!)
            } else if (responseStatus.status == ERROR) {
                emit(ApiResult.error(responseStatus.message!!))
                emitSource(source)
            }
        }
    }

fun <T> resultLiveData(
    scope: CoroutineScope,
    databaseQuery: () -> LiveData<T>
): LiveData<ApiResult<T>> =
    liveData(scope.coroutineContext) {
        emit(ApiResult.loading())

        withContext(Dispatchers.IO) {
            val source = databaseQuery.invoke().map { ApiResult.success(it) }
            emitSource(source)
        }
    }