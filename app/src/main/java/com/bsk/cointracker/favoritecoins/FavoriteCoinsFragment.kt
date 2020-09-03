package com.bsk.cointracker.favoritecoins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bsk.cointracker.R

class FavoriteCoinsFragment : Fragment() {

    private lateinit var favoriteCoinsViewModel: FavoriteCoinsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        return root
    }
}