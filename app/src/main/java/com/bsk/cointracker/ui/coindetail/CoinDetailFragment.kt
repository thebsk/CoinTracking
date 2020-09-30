package com.bsk.cointracker.ui.coindetail

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bsk.cointracker.BaseFragment
import com.bsk.cointracker.R
import com.bsk.cointracker.data.remote.common.ApiResult
import com.bsk.cointracker.data.remote.entities.Coin
import com.bsk.cointracker.databinding.FragmentCoinDetailBinding
import com.bsk.cointracker.util.hide
import com.bsk.cointracker.util.show
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CoinDetailFragment : BaseFragment<FragmentCoinDetailBinding>() {

    private val viewModel: CoinViewModel by viewModels()

    private val args: CoinDetailFragmentArgs by navArgs()

    override val layoutId: Int
        get() = R.layout.fragment_coin_detail

    override fun onViewBind(binding: FragmentCoinDetailBinding) = with(binding) {
        viewModel.coinId = args.coinId

        imgBack.setOnClickListener {
            findNavController().navigateUp()
        }

        subscribeUi(this)
    }

    private fun subscribeUi(binding: FragmentCoinDetailBinding) = with(binding) {
        viewModel.coin.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                ApiResult.Status.SUCCESS -> {
                    progressBar.hide()
                    binding.coin = result.data
                    result.data?.let { bindView(it) }
                }
                ApiResult.Status.LOADING -> progressBar.show()
                ApiResult.Status.ERROR -> {
                    progressBar.hide()
                    Snackbar.make(root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
        viewModel.coinById(args.coinId).observe(viewLifecycleOwner, Observer {
            val isFavCharacter = it.isNotEmpty()
            imgFavorite.apply {
                isSelected = isFavCharacter
                imgFavorite.visibility = View.VISIBLE
                setOnClickListener { v ->
                    if (v.isSelected) {
                        viewModel.removeCoin(coin!!)
                    } else {
                        viewModel.saveCoin(coin!!)
                    }
                }
            }
        })
    }

    private fun bindView(coin: Coin) {
        coin.apply {
        }
    }
}
