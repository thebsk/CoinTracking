package com.bsk.cointracker.coinlist.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bsk.cointracker.BaseFragment
import com.bsk.cointracker.R
import com.bsk.cointracker.coinlist.data.Coin
import com.bsk.cointracker.data.Result
import com.bsk.cointracker.databinding.FragmentCoinsBinding
import com.bsk.cointracker.di.common.Injectable
import com.bsk.cointracker.di.common.injectViewModel
import com.bsk.cointracker.util.hide
import com.bsk.cointracker.util.onTextChangedFlow
import com.bsk.cointracker.util.show
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

/**
 * A fragment representing a single Coin List screen.
 */
class CoinListFragment : BaseFragment(),
    Injectable {

    private lateinit var viewModel: CoinListViewModel

    private lateinit var set: Coin

    override val layoutId: Int
        get() = R.layout.fragment_coins

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit =
        with(binding as FragmentCoinsBinding) {
            super.onViewCreated(view, savedInstanceState)

            viewModel = injectViewModel(viewModelFactory)

            val adapter = CoinListAdapter()
            recyclerView.adapter = adapter

            subscribeUi(this, adapter)

            lifecycleScope.launch {
                searchView.onTextChangedFlow()
                    .debounce(400)
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
        adapter: CoinListAdapter
    ) = with(binding) {
        viewModel.searchCoins(query).observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    progressBar.hide()
                    result.data.also { adapter.submitList(it ?: emptyList()) }
                }
                Result.Status.LOADING -> progressBar.show()
                Result.Status.ERROR -> {
                    progressBar.hide()
                    Snackbar.make(root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun subscribeUi(binding: FragmentCoinsBinding, adapter: CoinListAdapter) =
        with(binding) {
            viewModel.coins.observe(viewLifecycleOwner, Observer { result ->
                when (result.status) {
                    Result.Status.SUCCESS -> {
                        progressBar.hide()
                        result.data.let { adapter.submitList(it ?: emptyList()) }
                    }
                    Result.Status.LOADING -> progressBar.show()
                    Result.Status.ERROR -> {
                        progressBar.hide()
                        Snackbar.make(root, result.message!!, Snackbar.LENGTH_LONG).show()
                    }
                }
            })
        }
}
