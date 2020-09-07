package com.bsk.cointracker.coinlist.ui

import androidx.recyclerview.widget.RecyclerView
import com.bsk.cointracker.R
import com.bsk.cointracker.coinlist.data.Coin
import com.bsk.cointracker.ui.BaseAdapter

/**
 * Adapter for the [RecyclerView] in [CoinListFragment].
 */
class CoinListAdapter(coinClickListener: (Coin) -> Unit) : BaseAdapter<Coin>(coinClickListener) {

    private var coins = ArrayList<Coin>()

    fun submitList(coins: List<Coin>) {
        this.coins.clear()
        this.coins.addAll(coins)
        notifyDataSetChanged()
    }

    override fun getItemCount() = coins.size

    override fun getItemForPosition(position: Int): Coin = coins[position]

    override fun getLayoutIdForPosition(position: Int): Int = R.layout.list_item_coin
}