package com.bsk.cointracker.data.background.workers

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bsk.cointracker.R
import com.bsk.cointracker.data.background.makeStatusNotification
import com.bsk.cointracker.data.remote.CoinService
import com.bsk.cointracker.di.qualifiers.CoroutineScopeIO
import com.bsk.cointracker.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import timber.log.Timber


class SyncCoinWorker @WorkerInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @CoroutineScopeIO private val scope: CoroutineScope,
    private val service: CoinService
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(scope.coroutineContext) {
        val coinId = inputData.getString(Constants.KEY_COIN_ID)!!
        val coinName = inputData.getString(Constants.KEY_COIN_NAME)!!

        return@withContext try {

            val response = service.getCoin(coinId)

            if (response.isSuccessful && response.body() != null) {
                makeStatusNotification(
                    coinName,
                    applicationContext.getString(R.string.coin_data_available) + " ${response.body()!!.price}",
                    applicationContext
                )
                Result.success()
            } else {
                Result.retry()
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            Timber.e(e)
            Result.failure()
        }
    }
}