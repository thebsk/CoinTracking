package com.bsk.cointracker

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.ViewDataBinding

abstract class RefreshableFragment<T : ViewDataBinding> : BaseFragment<T>() {

    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    private var timeInterval: Int? = null

    protected abstract fun onRefreshFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHandler = Handler(Looper.getMainLooper())
        mRunnable = Runnable {
            if (isVisible) {
                onRefreshFragment()
                startRefresh()
            }
        }
    }

    protected fun setTimerInterval(interval: Int?) {
        if (interval == null) {
            endRefresh()
        } else {
            timeInterval = interval * 1000 * 60
            startRefresh()
        }
    }

    override fun onResume() {
        super.onResume()
        if (isVisible) {
            startRefresh()
        } else {
            endRefresh()
        }
    }

    override fun onPause() {
        super.onPause()
        endRefresh()
    }

    override fun onDetach() {
        super.onDetach()
        endRefresh()
    }

    private fun startRefresh() {
        timeInterval ?: return
        mHandler.postDelayed(mRunnable, timeInterval!!.toLong())
    }

    private fun endRefresh() {
        mHandler.removeCallbacks(mRunnable)
    }
}