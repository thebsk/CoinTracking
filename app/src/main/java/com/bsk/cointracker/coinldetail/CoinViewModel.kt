package com.bsk.cointracker.coinldetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsk.cointracker.coinlist.data.CoinRepository
import javax.inject.Inject

/**
 * The ViewModel for [CoinDetailFragment].
 */
class CoinViewModel @Inject constructor(private val repository: CoinRepository) : ViewModel() {

    lateinit var coinId: String

    val coin by lazy {
        repository.observeCoin(viewModelScope, coinId)
    }
}
