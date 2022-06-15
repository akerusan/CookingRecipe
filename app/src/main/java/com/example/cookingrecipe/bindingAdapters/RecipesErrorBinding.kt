package com.example.cookingrecipe.bindingAdapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.cookingrecipe.data.database.RecipesEntity
import com.example.cookingrecipe.models.FoodRecipe
import com.example.cookingrecipe.utils.NetworkResult

class RecipesErrorBinding {

    companion object {

        @JvmStatic
        @BindingAdapter("getApiResponse", "getDatabase", requireAll = true)
        fun errorImageView(
            imageView: ImageView,
            response: NetworkResult<FoodRecipe>?,
            db: List<RecipesEntity>?
        ) {
            if (response is NetworkResult.Error && db.isNullOrEmpty()) {
                imageView.visibility = View.VISIBLE
            } else if (response is NetworkResult.Loading) {
                imageView.visibility = View.GONE
            } else if (response is NetworkResult.Success) {
                imageView.visibility = View.GONE
            }
        }

        @JvmStatic
        @BindingAdapter("getApiResponseForText", "getDatabaseForText", requireAll = true)
        fun errorTextView(
            textView: TextView,
            response: NetworkResult<FoodRecipe>?,
            db: List<RecipesEntity>?
        ) {
            if (response is NetworkResult.Error && db.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
                textView.text = response.message.toString()
            } else if (response is NetworkResult.Loading) {
                textView.visibility = View.GONE
            } else if (response is NetworkResult.Success) {
                textView.visibility = View.GONE
            }
        }
    }
}