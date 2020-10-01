package com.bsk.cointracker.ui.coindetail

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bsk.cointracker.BaseFragment
import com.bsk.cointracker.R
import com.bsk.cointracker.RefreshableFragment
import com.bsk.cointracker.data.remote.common.ApiResult
import com.bsk.cointracker.databinding.FragmentCoinDetailBinding
import com.bsk.cointracker.util.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CoinDetailFragment : BaseFragment<FragmentCoinDetailBinding>() {

    private val viewModel: CoinViewModel by viewModels()

    private val args: CoinDetailFragmentArgs by navArgs()

    override val layoutId: Int
        get() = R.layout.fragment_coin_detail

    @ExperimentalCoroutinesApi
    override fun onViewBind(binding: FragmentCoinDetailBinding) = with(binding) {
        viewModel.coinId = args.coinId

        imgBack.setOnClickListener {
            findNavController().navigateUp()
        }

        imgFavorite.setOnClickListener { v ->
            isUserAuthenticated(true) {
                if (it != true) return@isUserAuthenticated
                subscribeFavoriteCoin(args.coinId, this)

                if (v.isSelected) {
                    viewModel.removeCoin(coin!!)
                } else {
                    viewModel.saveCoin(coin!!)
                }
            }
        }

        lifecycleScope.launch {
            edtTimer.afterTextChangedFlow()
                .collect {
                    tvTimerDesc.apply {
                        isSelected = !it.isNullOrEmpty()
                        text =
                            if (!it.isNullOrEmpty()) "Timer is set to (minutes): " else "Set refresh interval in minutes: "
                    }
                    val time = if (it.isNullOrEmpty()) null else it.toString().toInt()
                    setTimerInterval(time)
                }
        }

        subscribeCoinDetail(this)
        isUserAuthenticated(false) {
            subscribeFavoriteCoin(args.coinId, this)
        }
    }

    private fun subscribeFavoriteCoin(coinId: String, binding: FragmentCoinDetailBinding) =
        with(binding) {
            viewModel.coinById(coinId).observe(viewLifecycleOwner, Observer {
                val isFavCharacter = it.isNotEmpty()
                imgFavorite.apply {
                    isSelected = isFavCharacter
                }
            })
        }

    private fun subscribeCoinDetail(binding: FragmentCoinDetailBinding) = with(binding) {
        viewModel.coin().observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                ApiResult.Status.SUCCESS -> {
                    progressBar.hide()
                    binding.coin = result.data
                }
                ApiResult.Status.LOADING -> progressBar.show()
                ApiResult.Status.ERROR -> {
                    progressBar.hide()
                    Snackbar.make(root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onRefreshFragment() {
        subscribeCoinDetail(binding)
    }
}
