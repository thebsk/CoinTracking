package com.bsk.cointracker.coinlist.data

import com.bsk.cointracker.api.BaseDataSource
import com.bsk.cointracker.api.CryptoService
import javax.inject.Inject

/**
 * Works with the Crypto API to get data.
 */
class CoinRemoteDataSource @Inject constructor(private val service: CryptoService) :
    BaseDataSource() {

    suspend fun fetchCoinList() = getResult { service.getCoinList() }

    suspend fun fetchCoin(id: String) = getResult { service.getSet(id) }
}
