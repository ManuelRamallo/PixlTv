package com.mramallo.pixltv.presentation.common

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadUrl(url: String){
    Glide.with(this)
        .load(url)
        .into(this)
}