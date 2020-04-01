package tech.jayamakmur.trackingapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

interface MyDataBindingUtil {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, imageUrl: String?) {
            imageUrl?.let { Glide.with(view.context).load(imageUrl).into(view) }
        }
    }
}