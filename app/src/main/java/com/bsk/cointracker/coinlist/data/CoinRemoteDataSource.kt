package com.bsk.cointracker.coinlist.data

import com.bsk.cointracker.api.BaseDataSource
import com.bsk.cointracker.api.CoinService
import javax.inject.Inject

/**
 * Works with the Crypto API to get data.
 */
class CoinRemoteDataSource @Inject constructor(private val service: CoinService) :
    BaseDataSource() {

    suspend fun fetchCoinList() = getResult { service.getCoinList() }

    suspend fun fetchCoin(id: String) = getResult { service.getCoin(id) }
}
