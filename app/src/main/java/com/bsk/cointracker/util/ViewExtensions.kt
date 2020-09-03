package com.bsk.cointracker.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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

fun ImageView.loadWithGlide(url: String) {
    url.let {
        Glide.with(this)
            .load(it)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .transition(DrawableTransitionOptions().crossFade(300))
            .into(this)
        return
    }
}

@BindingAdapter(
    "glideSrc",
    "glideCenterCrop",
    "glideCircularCrop",
    requireAll = false
)
fun ImageView.bindGlideSrc(
    url: String?,
    centerCrop: Boolean = false,
    circularCrop: Boolean = false
) {
    if (url == null) return

    createGlideRequest(
        context,
        url,
        centerCrop,
        circularCrop
    ).into(this)
}

private fun createGlideRequest(
    context: Context,
    url: String,
    centerCrop: Boolean,
    circularCrop: Boolean
): RequestBuilder<Drawable> {
    val req = Glide.with(context).load(url)
    if (centerCrop) req.centerCrop()
    if (circularCrop) req.circleCrop()
    return req
}