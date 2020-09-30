package com.bsk.cointracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    @get:LayoutRes
    abstract val layoutId: Int

    protected lateinit var binding: ViewDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater, layoutId, container, false
        ).apply {
            lifecycleOwner = this@BaseFragment
        }
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewBind(binding as T)
    }

    abstract fun onViewBind(binding: T)

    protected fun isUserAuthenticated(callback: (Boolean?) -> Unit) {
        if (activity !is MainActivity) throw IllegalStateException("Activity must be child of MainActivity.kt")

        (activity as MainActivity).checkIfUserIsAuthenticated(callback)
    }
}
