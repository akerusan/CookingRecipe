package com.example.cookingrecipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cookingrecipe.R
import com.example.cookingrecipe.databinding.ItemIngredientsBinding
import com.example.cookingrecipe.models.ExtendedIngredient
import com.example.cookingrecipe.utils.Constants.Companion.BASE_IMAGE_URL
import com.example.cookingrecipe.utils.RecipesDiffUtil

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemIngredientsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.ivIngredientsPicture.load(BASE_IMAGE_URL + ingredientsList[position].image) {
            crossfade(600)
            error(R.drawable.ic_picture_error)
        }
        holder.binding.tvIngredientName.text = ingredientsList[position].name.replaceFirstChar{ it.uppercase() }
        holder.binding.tvIngredientAmount.text = ingredientsList[position].amount.toString()
        holder.binding.tvIngredientUnit.text = ingredientsList[position].unit
        holder.binding.tvIngredientConsistency.text = ingredientsList[position].consistency
        holder.binding.tvIngredientOriginal.text = ingredientsList[position].original
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setData(newIngredientsList: List<ExtendedIngredient>) {
        val ingredientsDiffUtil = RecipesDiffUtil(ingredientsList, newIngredientsList)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)

        ingredientsList = newIngredientsList

        // Update UI if list has changed
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(val binding: ItemIngredientsBinding) : RecyclerView.ViewHolder(binding.root)
}