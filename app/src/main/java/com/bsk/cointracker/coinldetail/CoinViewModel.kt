package com.bsk.cointracker.coinldetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsk.cointracker.coinlist.data.CoinRepository
import com.bsk.cointracker.di.CoroutineScopeIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

/**
 * The ViewModel for [CoinDetailFragment].
 */
class CoinViewModel @Inject constructor(
    private val repository: CoinRepository,
    @CoroutineScopeIO private val ioCoroutineScope: CoroutineScope
) : ViewModel() {

    var connectivityAvailable: Boolean = false
    lateinit var coinId: String

    val coin by lazy {
        repository.observeCoin(viewModelScope, connectivityAvailable, coinId)
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }
}
