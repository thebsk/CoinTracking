package com.bsk.coiinttracker.coinlist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bsk.coiinttracker.coinlist.data.Coin
import com.bsk.coiinttracker.databinding.ListItemCoinBinding

/**
 * Adapter for the [RecyclerView] in [CoinListFragment].
 */
class CoinListAdapter : RecyclerView.Adapter<CoinListAdapter.CoinViewHolder>() {

    private var coins = ArrayList<Coin>()

    fun submitList(coins: List<Coin>) {
        this.coins.clear()
        this.coins.addAll(coins)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coin = getItem(position)
        holder.apply {
            bind(createOnClickListener(coin.name), coin)
            itemView.tag = coin
        }
    }

    override fun getItemCount() = coins.size

    private fun getItem(position: Int): Coin = coins[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(
            ListItemCoinBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private fun createOnClickListener(id: String): View.OnClickListener {
        return View.OnClickListener {
            CoinListFragmentDirections.actionNavigationCoinsToLegoSetFragment(id).run {
                it.findNavController().navigate(this)
            }
        }
    }

    class CoinViewHolder(private val binding: ListItemCoinBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            listener: View.OnClickListener, item: Coin
        ) {
            binding.apply {
                coin = item
                executePendingBindings()
            }
        }
    }
}