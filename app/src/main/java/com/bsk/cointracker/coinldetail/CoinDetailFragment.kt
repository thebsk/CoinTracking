package com.bsk.cointracker.coinldetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bsk.cointracker.R
import com.bsk.cointracker.coinlist.data.Coin
import com.bsk.cointracker.data.Result
import com.bsk.cointracker.databinding.FragmentCoinDetailBinding
import com.bsk.cointracker.di.Injectable
import com.bsk.cointracker.di.injectViewModel
import com.bsk.cointracker.util.setTitle
import javax.inject.Inject

/**
 * A fragment representing a single Coin detail screen.
 */
class CoinDetailFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: CoinViewModel

    private val args: CoinDetailFragmentArgs by navArgs()
    private lateinit var set: Coin

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
//        viewModel.id = args.coinId

        val binding = DataBindingUtil.inflate<FragmentCoinDetailBinding>(
            inflater, R.layout.fragment_coin_detail, container, false
        ).apply {
            lifecycleOwner = this@CoinDetailFragment
        }

        subscribeUi(binding)

        setHasOptionsMenu(true)
        return binding.root
    }

    @Suppress("DEPRECATION")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
//            R.id.action_share -> {
//                intentShareText(activity!!, getString(R.string.share_lego_set, set.name, set.url ?: ""))
//                return true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun subscribeUi(binding: FragmentCoinDetailBinding) {
        viewModel.coin.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
//                    binding.progressBar.hide()
                    result.data?.let { bindView(binding, it) }
                }
                Result.Status.LOADING -> /*binding.progressBar.show()*/ {
                }
                Result.Status.ERROR -> {
                }
            }
        })
    }

    private fun bindView(binding: FragmentCoinDetailBinding, coin: Coin) {
        coin.apply {
            setTitle(name)
//            bindImageFromUrl(binding.image, imageUrl)
//            binding.name.text = name
//            binding.year.text = getString(R.string.year, year ?: 0)
//            binding.numParts.text = getString(R.string.number_of_parts, numParts ?: 0)
            set = coin
        }
    }
}
