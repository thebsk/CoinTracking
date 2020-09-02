package com.bsk.coiinttracker.api

import com.bsk.coiinttracker.coinlist.data.Coin
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Lego REST API access points
 */
interface CryptoService {

    companion object {
        const val ENDPOINT = "https://api.coingecko.com/api/v3/"
    }

//    @GET("lego/themes/")
//    suspend fun getThemes(
//        @Query("page") page: Int? = null,
//        @Query("page_size") pageSize: Int? = null,
//        @Query("ordering") order: String? = null
//    ): Response<ResultsResponse<LegoTheme>>

    @GET("coins/list")
    suspend fun getCoinList(): Response<List<Coin>>

    @GET("lego/sets/{id}/")
    suspend fun getSet(@Path("id") id: String): Response<Coin>

}
