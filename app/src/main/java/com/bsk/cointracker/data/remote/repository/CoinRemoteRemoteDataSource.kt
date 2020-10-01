package com.bsk.cointracker.data.remote.repository

import com.bsk.cointracker.data.remote.BaseRemoteDataSource
import com.bsk.cointracker.data.remote.CoinService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinRemoteRemoteDataSource @Inject constructor(private val service: CoinService) :
    BaseRemoteDataSource() {

    suspend fun fetchCoinList() = getResult { service.getCoinList() }

    suspend fun fetchCoin(id: String) = getResult { service.getCoin(id) }
}
