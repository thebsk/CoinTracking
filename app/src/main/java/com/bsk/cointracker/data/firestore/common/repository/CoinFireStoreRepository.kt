package com.bsk.cointracker.data.firestore.common.repository

import com.bsk.cointracker.data.remote.entities.Coin
import com.bsk.cointracker.di.qualifiers.CoroutineScopeIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CoinFireStoreRepository @Inject constructor(
    @CoroutineScopeIO private val coroutineScope: CoroutineScope,
    private val fireStoreDataSource: CoinFireStoreDataSource
) {
    fun getFavoriteCoins(userId: String) =
        fireStoreDataSource.getFavoriteCoins(userId)

    fun getFavoriteCoinById(coinId: String, userId: String) =
        fireStoreDataSource.getFavoriteCoinById(coinId, userId)

    fun saveFavoriteCoin(coin: Coin) =
        fireStoreDataSource.saveFavoriteCoin(coin)

    fun deleteCoin(coin: Coin, userId: String) =
        coroutineScope.launch {
            fireStoreDataSource.delete(coin, userId).await().run {
                forEach {
                    it.reference.delete()
                }
            }
        }
}
