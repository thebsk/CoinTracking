package com.bsk.cointracker.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bsk.cointracker.util.glide.GlideApp


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

    GlideApp.with(view.context)
        .load(url).apply {
            if (centerCrop) centerCrop()
            if (circularCrop) circleCrop()
        }
        .into(view)
}