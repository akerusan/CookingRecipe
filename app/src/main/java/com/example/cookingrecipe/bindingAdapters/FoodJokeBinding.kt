package com.example.cookingrecipe.bindingAdapters

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.cookingrecipe.data.database.entities.FoodJokeEntity
import com.example.cookingrecipe.models.FoodJoke
import com.example.cookingrecipe.utils.NetworkResult
import com.google.android.material.card.MaterialCardView

class FoodJokeBinding {

    companion object {

        @JvmStatic
        @BindingAdapter("jokeApiResponse", "jokeDatabase", requireAll = false)
        fun setCardAndProgressVisibility(
            view: View,
            apiResponse: NetworkResult<FoodJoke>?,
            database: List<FoodJokeEntity>?
        ) {
            when (apiResponse) {
                is NetworkResult.Loading -> {
                    when (view) {
                        is ProgressBar -> view.visibility = View.VISIBLE
                        is MaterialCardView -> view.visibility = View.INVISIBLE
                    }
                }
                is NetworkResult.Error -> {
                    when (view) {
                        is ProgressBar -> view.visibility = View.INVISIBLE
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                            if (database != null && database.isEmpty()) view.visibility =
                                View.INVISIBLE
                        }
                    }
                }
                is NetworkResult.Success -> {
                    when (view) {
                        is ProgressBar -> view.visibility = View.INVISIBLE
                        is MaterialCardView -> view.visibility = View.VISIBLE
                    }
                }
                else -> {}
            }
        }

        @JvmStatic
        @BindingAdapter("jokeApiResponseError", "jokeErrorDatabaseError", requireAll = true)
        fun setErrorViewVisibility(
            view: View,
            apiResponse: NetworkResult<FoodJoke>?,
            database: List<FoodJokeEntity>?
        ) {
            if (database != null && database.isEmpty()) {
                view.visibility = View.VISIBLE
                if (view is TextView) {
                    if (apiResponse != null) {
                        view.text = apiResponse.message.toString()
                    }
                }
            }
            if (apiResponse is NetworkResult.Success) {
                view.visibility = View.INVISIBLE
            }
        }
    }
}