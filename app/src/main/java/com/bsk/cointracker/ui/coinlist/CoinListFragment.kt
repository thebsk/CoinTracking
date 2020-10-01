package com.bsk.cointracker.ui.coinlist

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bsk.cointracker.BaseFragment
import com.bsk.cointracker.R
import com.bsk.cointracker.data.remote.common.ApiResult
import com.bsk.cointracker.data.remote.entities.Coin
import com.bsk.cointracker.databinding.FragmentCoinsBinding
import com.bsk.cointracker.ui.adapters.CoinListAdapterSingleItem
import com.bsk.cointracker.util.hide
import com.bsk.cointracker.util.onTextChangedFlow
import com.bsk.cointracker.util.show
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CoinListFragment : BaseFragment<FragmentCoinsBinding>() {

    private val viewModel: CoinListViewModel by viewModels()

    private lateinit var set: Coin

    override val layoutId: Int
        get() = R.layout.fragment_coins

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun onViewBind(binding: FragmentCoinsBinding): Unit = with(binding) {
        val adapter = CoinListAdapterSingleItem {
            CoinListFragmentDirections.actionNavigationCoinsToCoinDetailFragment(
                it.id
            ).run {
                findNavController().navigate(this)
            }
        }
        recyclerView.adapter = adapter

        subscribeUi(this, adapter)

        lifecycleScope.launch {
            searchView.onTextChangedFlow()
                .debounce(400)
                .filter {
                    !it.isNullOrEmpty()
                }
                .distinctUntilChanged()
                .collect {
                    it?.let {
                        subscribeSearchUI(binding = this@with, query = it, adapter = adapter)
                    }
                }
        }
    }

    private fun subscribeSearchUI(
        binding: FragmentCoinsBinding,
        query: String,
        adapter: CoinListAdapterSingleItem
    ) = with(binding) {
        viewModel.searchCoins(query).observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                ApiResult.Status.SUCCESS -> {
                    progressBar.hide()
                    result.data.also { adapter.submitList(it ?: emptyList()) }
                }
                ApiResult.Status.LOADING -> progressBar.show()
                ApiResult.Status.ERROR -> {
                    progressBar.hide()
                    Snackbar.make(root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun subscribeUi(binding: FragmentCoinsBinding, adapter: CoinListAdapterSingleItem) =
        with(binding) {
            viewModel.coins.observe(viewLifecycleOwner, Observer { result ->
                when (result.status) {
                    ApiResult.Status.SUCCESS -> {
                        progressBar.hide()
                        result.data.let { adapter.submitList(it ?: emptyList()) }
                    }
                    ApiResult.Status.LOADING -> progressBar.show()
                    ApiResult.Status.ERROR -> {
                        progressBar.hide()
                        Snackbar.make(root, result.message!!, Snackbar.LENGTH_LONG).show()
                    }
                }
            })
        }
}
