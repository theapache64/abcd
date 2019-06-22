package com.theapache64.abcd.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * To load image from URL
 */
@BindingAdapter("imageUrl2")
fun loadImage(imageView: ImageView, url: String) {

    val requestOption = RequestOptions()

    Glide.with(imageView.context)
        .load(url)
        .apply(requestOption)
        .into(imageView)
}
