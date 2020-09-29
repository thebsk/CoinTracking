package com.bsk.cointracker.di.module


import com.bsk.cointracker.ui.coinldetail.CoinDetailFragment
import com.bsk.cointracker.ui.coinlist.CoinListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeCoinDetailFragment(): CoinDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeCoinListFragment(): CoinListFragment
}
