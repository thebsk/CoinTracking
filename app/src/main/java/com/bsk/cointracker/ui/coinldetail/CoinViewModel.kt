package com.bsk.cointracker.ui.coinldetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsk.cointracker.data.remote.repository.CoinRepository


class CoinViewModel @ViewModelInject constructor(private val repository: CoinRepository) :
    ViewModel() {

    lateinit var coinId: String

    val coin by lazy {
        repository.observeCoin(viewModelScope, coinId)
    }
}
