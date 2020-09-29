package com.bsk.cointracker.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


abstract class SingleItemBaseAdapter<T>(private val itemClickListener: ((T) -> Unit)? = null) :
    RecyclerView.Adapter<ItemViewHolder<T>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<T> {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding =
            DataBindingUtil.inflate<ViewDataBinding>(inflater, viewType, parent, false)
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder<T>, position: Int) {
        val item = getItemForPosition(position)
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(item)
        }

        holder.bind(item)
    }

    override fun getItemViewType(position: Int) = getLayoutIdForPosition(position)

    abstract fun getItemForPosition(position: Int): T

    abstract fun getLayoutIdForPosition(position: Int): Int
}