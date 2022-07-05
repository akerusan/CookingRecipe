package com.example.cookingrecipe.bindingAdapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.cookingrecipe.data.database.entities.RecipesEntity
import com.example.cookingrecipe.models.FoodRecipe
import com.example.cookingrecipe.utils.NetworkResult

class RecipesErrorBinding {

    companion object {

        @JvmStatic
        @BindingAdapter("getApiResponse", "getDatabase", requireAll = true)
        fun handleErrors(
            view: View,
            response: NetworkResult<FoodRecipe>?,
            db: List<RecipesEntity>?
        ) {
            when (view) {
                is ImageView -> view.isVisible = response is NetworkResult.Error && db.isNullOrEmpty()
                is TextView -> {
                    view.isVisible = response is NetworkResult.Error && db.isNullOrEmpty()
                    view.text = response?.message.toString()
                }
            }
        }
    }
}