package com.bsk.cointracker.util

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.SearchView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

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

@ExperimentalCoroutinesApi
fun EditText.afterTextChangedFlow(): Flow<Editable?> {
    return callbackFlow {
        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                offer(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        addTextChangedListener(watcher)
        awaitClose { removeTextChangedListener(watcher) }
    }
}