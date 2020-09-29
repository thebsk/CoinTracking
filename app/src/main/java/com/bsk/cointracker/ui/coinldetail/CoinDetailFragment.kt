package com.bsk.cointracker.ui.coinldetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bsk.cointracker.BaseFragment
import com.bsk.cointracker.R
import com.bsk.cointracker.data.remote.common.ApiResult
import com.bsk.cointracker.data.remote.entities.Coin
import com.bsk.cointracker.databinding.FragmentCoinDetailBinding
import com.bsk.cointracker.di.common.Injectable
import com.bsk.cointracker.di.common.injectViewModel
import com.bsk.cointracker.util.hide
import com.bsk.cointracker.util.setTitle
import com.bsk.cointracker.util.show
import com.google.android.material.snackbar.Snackbar

/**
 * A fragment representing a single Coin Detail screen.
 */
class CoinDetailFragment : BaseFragment(),
    Injectable {

    private lateinit var viewModel: CoinViewModel

    private val args: CoinDetailFragmentArgs by navArgs()

    override val layoutId: Int
        get() = R.layout.fragment_coin_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) =
        with(binding as FragmentCoinDetailBinding) {
            super.onViewCreated(view, savedInstanceState)

            viewModel = injectViewModel(viewModelFactory)
            viewModel.coinId = args.coinId

            subscribeUi(this)
            setHasOptionsMenu(true)
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_favorite, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                Toast.makeText(requireContext(), "asda", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
    }

    private fun bindView(coin: Coin) {
        coin.apply {
            setTitle(name)
        }
    }
}
