package com.bsk.cointracker.ui.coindetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsk.cointracker.data.remote.entities.Coin
import com.bsk.cointracker.data.remote.repository.AuthRepository
import com.bsk.cointracker.data.remote.repository.CoinFireStoreRepository
import com.bsk.cointracker.data.remote.repository.CoinRepository


class CoinViewModel @ViewModelInject constructor(
    repository: CoinRepository,
    private val fireStoreRepository: CoinFireStoreRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    lateinit var coinId: String

    val coin by lazy {
        repository.observeCoin(viewModelScope, coinId)
    }

    fun coinById(coinId: String) =
        fireStoreRepository.getFavoriteCoinById(coinId, authRepository.user?.uid ?: "")

    fun saveCoin(coin: Coin) =
        coin.apply {
            userId = authRepository.user!!.uid
        }.let {
            fireStoreRepository.saveFavoriteCoin(it)
        }

    fun removeCoin(coin: Coin) =
        fireStoreRepository.deleteCoin(coin, authRepository.user!!.uid)
}
