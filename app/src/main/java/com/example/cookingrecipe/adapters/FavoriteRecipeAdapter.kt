package com.example.cookingrecipe.adapters

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingrecipe.R
import com.example.cookingrecipe.data.database.entities.FavoritesEntity
import com.example.cookingrecipe.databinding.ItemFavoritesBinding
import com.example.cookingrecipe.ui.fragment.favoriteScreen.FavoriteRecipesFragmentDirections
import com.example.cookingrecipe.utils.Commons.Companion.showSnackBar
import com.example.cookingrecipe.utils.RecipesDiffUtil
import com.example.cookingrecipe.viewModels.MainViewModel

class FavoriteRecipeAdapter(
    private val requireActivity: FragmentActivity,
    private val viewModel: MainViewModel
    ) : RecyclerView.Adapter<FavoriteRecipeAdapter.FavoriteViewHolder>(), ActionMode.Callback {

    private var multiSelection = false

    private lateinit var mActionMode: ActionMode
    private lateinit var rootView: View

    private var selectedRecipesList = arrayListOf<FavoritesEntity>()
    private var favoriteViewHolders = arrayListOf<FavoriteViewHolder>()
    private var favoriteRecipesList = emptyList<FavoritesEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        favoriteViewHolders.add(holder)
        rootView = holder.itemView.rootView

        val recipe = favoriteRecipesList[position]
        holder.bind(recipe)

        // Send to the recipe details screen
        holder.binding.favoritesItem.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, recipe)
            } else {
                val action = FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(recipe.result)
                holder.itemView.findNavController().navigate(action)
            }
        }
        // Delete action mode by long clicking
        holder.binding.favoritesItem.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, recipe)
                true
            } else {
                multiSelection = false
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return favoriteRecipesList.size
    }

    private fun applySelection(viewHolder: FavoriteViewHolder, recipe: FavoritesEntity) {
        if (selectedRecipesList.contains(recipe)) {
            selectedRecipesList.remove(recipe)
            changeRecipeStyle(viewHolder, R.color.cardBackgroundColor, R.color.strokeColor)
            applyActionModeTitle()
        } else {
            selectedRecipesList.add(recipe)
            changeRecipeStyle(viewHolder, R.color.selectedMainColor, R.color.green_500)
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(viewHolder: FavoriteViewHolder, backgroundColor: Int, strokeColor: Int) {
        viewHolder.binding.clFavoriteRecipeCard.setBackgroundColor(ContextCompat.getColor(requireActivity, backgroundColor))
        viewHolder.binding.favoriteCardViewItem.strokeColor = ContextCompat.getColor(requireActivity, strokeColor)
    }

    private fun applyActionModeTitle() {
        when(selectedRecipesList.size) {
            0 -> {
                mActionMode.finish()
            }
            1 -> {
                mActionMode.title = "1 item selected"
            }
            else -> {
                mActionMode.title = "${selectedRecipesList.size} items selected"
            }
        }
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
        mActionMode = actionMode!!
        changeStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if (menu?.itemId == R.id.delete_favorite_recipe_menu) {
            selectedRecipesList.forEach { favoriteRecipe ->
                viewModel.deleteFavoriteRecipe(favoriteRecipe)
            }
            showSnackBar(rootView, "${selectedRecipesList.size} recipes deleted")

            // Reinitialization
            multiSelection = false
            selectedRecipesList.clear()
            actionMode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        favoriteViewHolders.forEach { holder ->
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }

        multiSelection = false
        selectedRecipesList.clear()
        changeStatusBarColor(R.color.statusBarColor)
    }

    private fun changeStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    fun setData(newFavoritesList: List<FavoritesEntity>) {
        // Check for changes in the list
        val favoritesDiffUtil = RecipesDiffUtil(favoriteRecipesList, newFavoritesList)
        val diffUtilResult = DiffUtil.calculateDiff(favoritesDiffUtil)

        favoriteRecipesList = newFavoritesList

        // Update UI if list has changed
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun stopContextualActionMode() {
        if (this::mActionMode.isInitialized) {
            mActionMode.finish()
        }
    }

    class FavoriteViewHolder(val binding: ItemFavoritesBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FavoriteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFavoritesBinding.inflate(layoutInflater, parent, false)
                return FavoriteViewHolder(binding)
            }
        }
    }
}