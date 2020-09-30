package com.bsk.cointracker.ui.favoritecoins

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.bsk.cointracker.data.remote.repository.AuthRepository
import com.bsk.cointracker.data.remote.repository.CoinFireStoreRepository


class FavoriteCoinsViewModel @ViewModelInject constructor(
    repository: CoinFireStoreRepository,
    authRepository: AuthRepository
) : ViewModel() {

    val favoriteCoins = repository.getFavoriteCoins(authRepository.user.uid)
}