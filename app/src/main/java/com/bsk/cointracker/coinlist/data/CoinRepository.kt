package com.bsk.cointracker.coinlist.data

import androidx.lifecycle.distinctUntilChanged
import com.bsk.cointracker.data.resultLiveData
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class CoinRepository @Inject constructor(
    private val dao: CoinDao,
    private val coinRemoteDataSource: CoinRemoteDataSource
) {
    fun observeCoin(scope: CoroutineScope, connectivityAvailable: Boolean, id: String) =
        resultLiveData(
            scope = scope,
            databaseQuery = { dao.getCoin(id) },
            networkCall = { coinRemoteDataSource.fetchCoin(id) },
            saveCallResult = { dao.insert(it) }
        ).distinctUntilChanged()

    fun observeCoins(scope: CoroutineScope) = resultLiveData(
        scope = scope,
        databaseQuery = { dao.getCoins() },
        networkCall = { coinRemoteDataSource.fetchCoinList() },
        saveCallResult = { dao.insertAll(it) }
    )

    fun searchCoins(scope: CoroutineScope, query: String) = resultLiveData(
        scope = scope,
        databaseQuery = { dao.searchCoin(query) }
    )

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: CoinRepository? = null

        fun getInstance(dao: CoinDao, coinRemoteDataSource: CoinRemoteDataSource) =
            instance ?: synchronized(this) {
                instance
                    ?: CoinRepository(dao, coinRemoteDataSource).also { instance = it }
            }
    }
}
