package com.bsk.cointracker.ui.coindetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsk.cointracker.data.background.SyncCoinWorkerHelper
import com.bsk.cointracker.data.remote.common.ApiResult
import com.bsk.cointracker.data.remote.entities.Coin
import com.bsk.cointracker.data.remote.repository.AuthRepository
import com.bsk.cointracker.data.remote.repository.CoinFireStoreRepository
import com.bsk.cointracker.data.remote.repository.CoinRepository


class CoinViewModel @ViewModelInject constructor(
    private val repository: CoinRepository,
    private val fireStoreRepository: CoinFireStoreRepository,
    private val authRepository: AuthRepository,
    private val syncCoinWorkerHelper: SyncCoinWorkerHelper
) : ViewModel() {

    lateinit var coinId: String
    private lateinit var coin: LiveData<ApiResult<Coin>>

    fun coin(): LiveData<ApiResult<Coin>> {
        coin = repository.observeCoin(viewModelScope, coinId)
        return coin
    }

    fun coinById(coinId: String) =
        fireStoreRepository.getFavoriteCoinById(coinId, authRepository.user?.uid ?: "")

    fun saveCoin(coin: Coin) =
        coin.apply {
            userId = authRepository.user!!.uid
        }.let {
            fireStoreRepository.saveFavoriteCoin(it)
            syncCoinWorkerHelper.startWorker(it)
        }

    fun removeCoin(coin: Coin) {
        fireStoreRepository.deleteCoin(coin, authRepository.user!!.uid)
        syncCoinWorkerHelper.cancelWork(coin)
    }
}
