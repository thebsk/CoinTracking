package com.bsk.cointracker.ui.favoritecoins

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.bsk.cointracker.data.remote.repository.CoinFireStoreRepository


class FavoriteCoinsViewModel @ViewModelInject constructor(
    repository: CoinFireStoreRepository
) : ViewModel() {

    val favoriteCoins = repository.getFavoriteCoins()
}