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

    protected lateinit var binding: T

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater, layoutId, container, false
        ).apply {
            lifecycleOwner = this@BaseFragment
        } as T
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewBind(binding)
    }

    abstract fun onViewBind(binding: T)

    @Throws(IllegalStateException::class)
    protected fun isUserAuthenticated(autoLogin: Boolean, callback: ((Boolean?) -> Unit)? = null) {
        if (activity !is MainActivity) throw IllegalStateException("Activity must be child of MainActivity.kt")

        (activity as MainActivity).checkIfUserIsAuthenticated(
            showLogin = autoLogin,
            callback = callback
        )
    }
}
