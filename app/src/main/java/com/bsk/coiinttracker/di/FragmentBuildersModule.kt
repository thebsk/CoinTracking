package com.bsk.coiinttracker.di


import com.bsk.coiinttracker.coinldetail.CoinDetailFragment
import com.bsk.coiinttracker.coinlist.ui.CoinListFragment
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
