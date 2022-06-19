package com.example.cookingrecipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingrecipe.data.database.entities.FavoritesEntity
import com.example.cookingrecipe.databinding.ItemFavoritesBinding
import com.example.cookingrecipe.utils.RecipesDiffUtil

class FavoriteRecipeAdapter: RecyclerView.Adapter<FavoriteRecipeAdapter.ViewHolder>() {

    private var favoriteRecipesList = emptyList<FavoritesEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val selectedRecipe = favoriteRecipesList[position]
        holder.bind(selectedRecipe)
    }

    override fun getItemCount(): Int {
        return favoriteRecipesList.size
    }

    fun setData(newFavoritesList: List<FavoritesEntity>) {
        // Check for changes in the list
        val favoritesDiffUtil = RecipesDiffUtil(favoriteRecipesList, newFavoritesList)
        val diffUtilResult = DiffUtil.calculateDiff(favoritesDiffUtil)

        favoriteRecipesList = newFavoritesList

        // Update UI if list has changed
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: ItemFavoritesBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFavoritesBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}