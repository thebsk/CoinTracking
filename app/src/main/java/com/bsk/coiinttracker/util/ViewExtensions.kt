package com.bsk.coiinttracker.util

import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun Fragment.setTitle(title: String) {
    (activity as AppCompatActivity).supportActionBar!!.title = title
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

@ExperimentalCoroutinesApi
fun SearchView.onTextChangedFlow(): Flow<String?> {
    return callbackFlow {
        val listener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                offer(p0)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                offer(p0)
                return false
            }
        }

        setOnQueryTextListener(listener)
        awaitClose { setOnQueryTextListener(null) }
    }
}