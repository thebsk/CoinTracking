package com.bsk.cointracker.data.remote.repository

import com.bsk.cointracker.data.remote.entities.Coin
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CoinFireStoreRepository @Inject constructor(
    private val fireStoreDataSource: CoinFireStoreDataSource
) {
    fun getFavoriteCoins() =
        fireStoreDataSource.getFavoriteCoins()

    fun getFavoriteCoinById(coinId: String) =
        fireStoreDataSource.getFavoriteCoinById(coinId)

    fun saveFavoriteCoin(coin: Coin) =
        fireStoreDataSource.saveFavoriteCoin(coin)

    fun deleteCoin(coin: Coin) =
        fireStoreDataSource.delete(coin)
}
