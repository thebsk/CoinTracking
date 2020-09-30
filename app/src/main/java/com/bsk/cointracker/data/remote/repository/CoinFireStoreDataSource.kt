package com.bsk.cointracker.data.remote.repository

import com.bsk.cointracker.data.firestore.common.livedata
import com.bsk.cointracker.data.remote.entities.Coin
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject


class CoinFireStoreDataSource @Inject constructor(private val firestore: FirebaseFirestore) {

    fun getFavoriteCoins() =
        firestore
            .collection("coins")
            .livedata(Coin::class.java)

    fun saveFavoriteCoin(coin: Coin) {
        firestore
            .collection("coins")
            .document(coin.id)
            .set(coin)
    }

    fun getFavoriteCoinById(coinId: String) =
        firestore.collection("coins")
            .whereEqualTo("id", coinId)
            .livedata(Coin::class.java)

    fun delete(coin: Coin) {
        firestore.collection("coins")
            .document(coin.id)
            .delete()
    }
}
