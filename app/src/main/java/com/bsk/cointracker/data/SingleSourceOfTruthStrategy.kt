package com.bsk.cointracker.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.bsk.cointracker.data.Result.Status.ERROR
import com.bsk.cointracker.data.Result.Status.SUCCESS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * The database serves as the single source of truth.
 * Therefore UI can receive data updates from database only.
 * Function notify UI about:
 * [Result.Status.SUCCESS] - with data from database
 * [Result.Status.ERROR] - if error has occurred from any source
 * [Result.Status.LOADING]
 */
fun <T, A> resultLiveData(
    scope: CoroutineScope,
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> Result<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<Result<T>> =
    liveData(scope.coroutineContext) {
        emit(Result.loading())

        withContext(Dispatchers.IO) {
            val source = databaseQuery.invoke().map { Result.success(it) }
            emitSource(source)

            val responseStatus = networkCall.invoke()
            if (responseStatus.status == SUCCESS) {
                saveCallResult(responseStatus.data!!)
            } else if (responseStatus.status == ERROR) {
                emit(Result.error(responseStatus.message!!))
                emitSource(source)
            }
        }
    }

fun <T> resultLiveData(
    scope: CoroutineScope,
    databaseQuery: () -> LiveData<T>
): LiveData<Result<T>> =
    liveData(scope.coroutineContext) {
        emit(Result.loading())

        withContext(Dispatchers.IO) {
            val source = databaseQuery.invoke().map { Result.success(it) }
            emitSource(source)
        }
    }