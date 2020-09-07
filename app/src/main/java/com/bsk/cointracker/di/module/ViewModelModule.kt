package com.bsk.cointracker.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bsk.cointracker.coinldetail.CoinViewModel
import com.bsk.cointracker.coinlist.ui.CoinListViewModel
import com.bsk.cointracker.di.common.ViewModelFactory
import com.bsk.cointracker.di.common.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CoinViewModel::class)
    abstract fun bindCoinViewModel(viewModel: CoinViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CoinListViewModel::class)
    abstract fun bindCoinListViewModel(viewModel: CoinListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
