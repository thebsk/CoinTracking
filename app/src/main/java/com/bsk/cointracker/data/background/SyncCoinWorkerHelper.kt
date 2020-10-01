package com.bsk.cointracker.data.background

import androidx.work.*
import com.bsk.cointracker.data.background.workers.SyncCoinWorker
import com.bsk.cointracker.data.remote.entities.Coin
import com.bsk.cointracker.util.Constants
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SyncCoinWorkerHelper @Inject constructor(
    private val workManager: WorkManager
) {
    private fun createInputDataForCoin(coin: Coin) =
        Data.Builder().apply {
            putString(Constants.KEY_COIN_ID, coin.id)
            putString(Constants.KEY_COIN_NAME, coin.name)
        }.build()

    fun startWorker(coin: Coin) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicSyncDataWork = PeriodicWorkRequest.Builder(
            SyncCoinWorker::class.java, WORK_TIME_INTERVAL, TimeUnit.MINUTES
        )
            .addTag(coin.id)
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .setInputData(createInputDataForCoin(coin))
            .build()

        workManager.enqueueUniquePeriodicWork(
            Constants.WORK_NAME_SYNC_COIN,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicSyncDataWork
        )
    }

    fun cancelWork(coin: Coin) {
        workManager.cancelAllWorkByTag(coin.id)
    }

    companion object {
        const val WORK_TIME_INTERVAL = 15L
    }
}