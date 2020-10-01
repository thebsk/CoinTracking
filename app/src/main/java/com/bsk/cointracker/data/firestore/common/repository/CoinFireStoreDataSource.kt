package com.bsk.cointracker.data.firestore.common.repository

import com.bsk.cointracker.data.firestore.common.livedata
import com.bsk.cointracker.data.remote.entities.Coin
import com.bsk.cointracker.util.Constants
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject


class CoinFireStoreDataSource @Inject constructor(private val firestore: FirebaseFirestore) {

    fun getFavoriteCoins(userId: String) =
        firestore
            .collection(Constants.KEY_COINS)
            .whereEqualTo(Constants.KEY_USER_ID, userId)
            .livedata(Coin::class.java)

    fun saveFavoriteCoin(coin: Coin) {
        firestore
            .collection(Constants.KEY_COINS)
            .add(coin)
    }

    fun getFavoriteCoinById(coinId: String, userId: String) =
        firestore.collection(Constants.KEY_COINS)
            .whereEqualTo(Constants.KEY_USER_ID, userId)
            .whereEqualTo(Constants.KEY_ID, coinId)
            .livedata(Coin::class.java)

    fun delete(coin: Coin, userId: String) =
        firestore.collection(Constants.KEY_COINS)
            .whereEqualTo(Constants.KEY_ID, coin.id)
            .whereEqualTo(Constants.KEY_USER_ID, userId)
            .get()
}
