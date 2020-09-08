package com.bsk.cointracker.binding

import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("renderHtml")
fun bindRenderHtml(view: TextView, description: String?) {
    if (description != null) {
        view.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.movementMethod = LinkMovementMethod.getInstance()
    } else {
        view.text = ""
    }
}

@BindingAdapter(
    "imageUrl",
    "centerCrop",
    "circularCrop",
    requireAll = false
)
fun bindImageFromUrl(
    view: ImageView,
    url: String?,
    centerCrop: Boolean = false,
    circularCrop: Boolean = false
) {
    if (url == null) return

    Glide.with(view.context).load(url).apply {
        if (centerCrop) centerCrop()
        if (circularCrop) circleCrop()
    }.into(view)
}