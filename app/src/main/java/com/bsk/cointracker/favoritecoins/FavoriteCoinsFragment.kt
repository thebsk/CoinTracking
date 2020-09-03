package com.bsk.cointracker.favoritecoins

import android.os.Bundle
import android.view.View
import com.bsk.cointracker.BaseFragment
import com.bsk.cointracker.R
import com.bsk.cointracker.databinding.FragmentFavoriteCoinsBinding
import com.bsk.cointracker.di.Injectable
import com.bsk.cointracker.di.injectViewModel

class FavoriteCoinsFragment : BaseFragment(), Injectable {

    private lateinit var favoriteCoinsViewModel: FavoriteCoinsViewModel

    override val layoutId: Int
        get() = R.layout.fragment_favorite_coins

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) =
        with(binding as FragmentFavoriteCoinsBinding) {
            super.onViewCreated(view, savedInstanceState)

            favoriteCoinsViewModel = injectViewModel(viewModelFactory)
        }
}