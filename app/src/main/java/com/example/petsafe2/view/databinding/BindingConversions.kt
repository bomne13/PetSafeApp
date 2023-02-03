package com.example.petsafe2.view.databinding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingConversions {

    @BindingAdapter("imageUrl","error")
    @JvmStatic
    fun loadImage(imageView: ImageView?, url: String?, error: Drawable){
        imageView?.context?.let {
            Glide.with(it).load(url)
                .error(error)
                .into(imageView)
        }
    }
}