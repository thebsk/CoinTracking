package com.bsk.cointracker.ui.coinlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsk.cointracker.data.remote.repository.CoinRepository


class CoinListViewModel @ViewModelInject constructor(private val repository: CoinRepository) :
    ViewModel() {

    val coins by lazy { repository.observeCoins(viewModelScope) }

    fun searchCoins(query: String) = repository.searchCoins(viewModelScope, query)
}