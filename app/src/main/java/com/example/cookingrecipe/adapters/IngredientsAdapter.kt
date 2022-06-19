package com.example.cookingrecipe.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cookingrecipe.R
import com.example.cookingrecipe.models.ExtendedIngredient
import com.example.cookingrecipe.utils.Constants.Companion.BASE_IMAGE_URL
import com.example.cookingrecipe.utils.RecipesDiffUtil
import kotlinx.android.synthetic.main.ingredients_item.view.*

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ingredients_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.iv_ingredients_picture.load(BASE_IMAGE_URL + ingredientsList[position].image) {
            crossfade(600)
            error(R.drawable.ic_picture_error)
        }
        holder.itemView.tv_ingredient_name.text = ingredientsList[position].name.replaceFirstChar{ it.uppercase() }
        holder.itemView.tv_ingredient_amount.text = ingredientsList[position].amount.toString()
        holder.itemView.tv_ingredient_unit.text = ingredientsList[position].unit
        holder.itemView.tv_ingredient_consistency.text = ingredientsList[position].consistency
        holder.itemView.tv_ingredient_original.text = ingredientsList[position].original
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}