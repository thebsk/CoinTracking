package com.bsk.cointracker.ui.coinlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsk.cointracker.data.remote.repository.CoinRepository
import javax.inject.Inject

/**
 * The ViewModel used in [CoinListFragment].
 */
class CoinListViewModel @Inject constructor(private val repository: CoinRepository) : ViewModel() {

    val coins by lazy { repository.observeCoins(viewModelScope) }

    fun searchCoins(query: String) = repository.searchCoins(viewModelScope, query)
}