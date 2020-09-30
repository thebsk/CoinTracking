package com.bsk.cointracker.ui.favoritecoins

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bsk.cointracker.BaseFragment
import com.bsk.cointracker.R
import com.bsk.cointracker.databinding.FragmentFavoriteCoinsBinding
import com.bsk.cointracker.ui.adapters.FavoriteCoinsAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteCoinsFragment : BaseFragment<FragmentFavoriteCoinsBinding>() {

    private val favoriteCoinsViewModel: FavoriteCoinsViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.fragment_favorite_coins

    override fun onViewBind(binding: FragmentFavoriteCoinsBinding) = with(binding) {
        rvFavoriteCoins.apply {
            adapter = coinAdapter
        }

        subscribeUi(this)
    }

    private fun subscribeUi(binding: FragmentFavoriteCoinsBinding) = with(binding) {
        favoriteCoinsViewModel.favoriteCoins.observe(viewLifecycleOwner, Observer {
            showPlaceHolder = it.isEmpty()
            (rvFavoriteCoins.adapter as FavoriteCoinsAdapter).submitList(it)
            rvFavoriteCoins.scheduleLayoutAnimation()
        })
    }

    private val coinAdapter by lazy {
        FavoriteCoinsAdapter {
            FavoriteCoinsFragmentDirections.actionNavigationFavoriteCoinsToCoinDetailFragment(
                it.id
            ).run {
                findNavController().navigate(this)
            }
        }
    }
}