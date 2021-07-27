package com.example.bookapp.presentation.adapter.binding

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import com.example.bookapp.R

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()
        imageView.load(imgUri) {
            error(R.drawable.ic_error_image)
        }
    }
}