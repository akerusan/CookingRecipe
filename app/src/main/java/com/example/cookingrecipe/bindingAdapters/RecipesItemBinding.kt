package com.example.cookingrecipe.bindingAdapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.cookingrecipe.R

class RecipesItemBinding {

    companion object {

        @JvmStatic
        @BindingAdapter("loadImageUrl")
        fun loadImageUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(600)
                error(R.drawable.ic_picture_error)
            }
        }

        @JvmStatic
        @BindingAdapter("setLikes")
        fun setLikes(textView: TextView, likes: Int) {
            textView.text = likes.toString()
        }

        @JvmStatic
        @BindingAdapter("setTime")
        fun setTime(textView: TextView, time: Int) {
            textView.text = time.toString()
        }

        @JvmStatic
        @BindingAdapter("isVegan")
        fun isVegan(view: View, isVegan: Boolean) {
            if (isVegan) {
                when(view) {
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(view.context, R.color.green)
                        )
                    }
                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(view.context, R.color.green)
                        )
                    }
                }
            }
        }

    }
}