package com.bsk.cointracker.data.remote.repository

import com.bsk.cointracker.data.remote.BaseDataSource
import com.bsk.cointracker.data.remote.CoinService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinRemoteDataSource @Inject constructor(private val service: CoinService) :
    BaseDataSource() {

    suspend fun fetchCoinList() = getResult { service.getCoinList() }

    suspend fun fetchCoin(id: String) = getResult { service.getCoin(id) }
}
