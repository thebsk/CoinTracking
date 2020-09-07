package com.bsk.cointracker.ui

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bsk.cointracker.BR

open class ItemViewHolder<T>(private val itemBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(item: T) {
        itemBinding.setVariable(BR.item, item)
        itemBinding.executePendingBindings()
    }
}