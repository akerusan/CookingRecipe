package com.example.cookingrecipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingrecipe.databinding.RecipesItemBinding
import com.example.cookingrecipe.models.FoodRecipe
import com.example.cookingrecipe.models.Result
import com.example.cookingrecipe.utils.RecipesDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    private var recipeList = emptyList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRecipe = recipeList[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    fun setData(newList: FoodRecipe) {
        // Check for changes in the list
        val recipesDiffUtil = RecipesDiffUtil(recipeList, newList.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)

        recipeList = newList.results

        // Update UI if list has changed
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: RecipesItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Result) {
            binding.recipe = recipe
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}