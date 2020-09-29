package com.bsk.cointracker.ui.favoritecoins

import androidx.fragment.app.viewModels
import com.bsk.cointracker.BaseFragment
import com.bsk.cointracker.R
import com.bsk.cointracker.databinding.FragmentFavoriteCoinsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteCoinsFragment : BaseFragment<FragmentFavoriteCoinsBinding>() {

    private val favoriteCoinsViewModel: FavoriteCoinsViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.fragment_favorite_coins

    override fun onViewBind(binding: FragmentFavoriteCoinsBinding) {
    }
}