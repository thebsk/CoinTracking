package com.bsk.coiinttracker.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bsk.coiinttracker.coinldetail.CoinViewModel
import com.bsk.coiinttracker.coinlist.ui.CoinListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CoinViewModel::class)
    abstract fun bindLegoSetsViewModel(viewModel: CoinViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CoinListViewModel::class)
    abstract fun bindLegoSetViewModel(viewModel: CoinListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
