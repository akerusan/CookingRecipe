package com.example.cookingrecipe.bindingAdapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.cookingrecipe.R
import com.example.cookingrecipe.fragment.listScreen.RecipesListFragmentDirections
import com.example.cookingrecipe.models.Result
import org.jsoup.Jsoup
import java.lang.Exception

class RecipesItemBinding {

    companion object {

        @JvmStatic
        @BindingAdapter("onRecipeClickListener")
        fun onRecipeClickListener(recipeItemLayout: ConstraintLayout, result: Result) {
            recipeItemLayout.setOnClickListener {
                try {
                    val action = RecipesListFragmentDirections.actionRecipesFragmentToDetailsActivity(result)
                    recipeItemLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onRecipeClickListenerError", e.toString())
                }
            }
        }

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

        @JvmStatic
        @BindingAdapter("parseHtml")
        fun parseHtml(textView: TextView, description: String?) {
            if (description != null) {
                textView.text = Jsoup.parse(description).text()
            }
        }
    }
}