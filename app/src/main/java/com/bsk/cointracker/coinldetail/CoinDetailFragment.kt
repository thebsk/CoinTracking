package com.bsk.cointracker.coinldetail

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
import com.bsk.cointracker.coinlist.data.Coin
import com.bsk.cointracker.data.Result
import com.bsk.cointracker.databinding.FragmentCoinDetailBinding
import com.bsk.cointracker.di.Injectable
import com.bsk.cointracker.di.injectViewModel
import com.bsk.cointracker.util.hide
import com.bsk.cointracker.util.setTitle
import com.bsk.cointracker.util.show
import com.google.android.material.snackbar.Snackbar

/**
 * A fragment representing a single Coin Detail screen.
 */
class CoinDetailFragment : BaseFragment(), Injectable {

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
                Result.Status.SUCCESS -> {
                    progressBar.hide()
                    binding.coin = result.data
                    result.data?.let { bindView(it) }
                }
                Result.Status.LOADING -> progressBar.show()
                Result.Status.ERROR -> {
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
