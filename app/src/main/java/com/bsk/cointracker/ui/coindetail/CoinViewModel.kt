package com.bsk.cointracker.ui.coindetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsk.cointracker.data.remote.entities.Coin
import com.bsk.cointracker.data.remote.repository.CoinFireStoreRepository
import com.bsk.cointracker.data.remote.repository.CoinRepository


class CoinViewModel @ViewModelInject constructor(
    repository: CoinRepository,
    private val fireStoreRepository: CoinFireStoreRepository
) : ViewModel() {

    lateinit var coinId: String

    val coin by lazy {
        repository.observeCoin(viewModelScope, coinId)
    }

    fun coinById(coinId: String) = fireStoreRepository.getFavoriteCoinById(coinId)

    fun saveCoin(coin: Coin) = fireStoreRepository.saveFavoriteCoin(coin)

    fun removeCoin(coin: Coin) = fireStoreRepository.deleteCoin(coin)
}
