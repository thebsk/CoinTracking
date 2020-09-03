package com.bsk.cointracker.api

import com.bsk.cointracker.coinlist.data.Coin
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Lego REST API access points
 */
interface CoinService {

    companion object {
        const val ENDPOINT = "https://api.coingecko.com/api/v3/"
    }

    @GET("coins/list")
    suspend fun getCoinList(): Response<List<Coin>>

    @GET("coins/{id}")
    suspend fun getCoin(@Path("id") id: String): Response<Coin>
}