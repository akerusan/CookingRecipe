package com.example.cookingrecipe.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.cookingrecipe.R
import com.example.cookingrecipe.adapters.PagerAdapter
import com.example.cookingrecipe.data.database.entities.FavoritesEntity
import com.example.cookingrecipe.databinding.ActivityDetailsBinding
import com.example.cookingrecipe.ui.fragment.detailScreen.RecipeDetailsFragment
import com.example.cookingrecipe.ui.fragment.detailScreen.RecipeIngredientsFragment
import com.example.cookingrecipe.ui.fragment.detailScreen.RecipeInstructionsFragment
import com.example.cookingrecipe.utils.Commons.Companion.showSnackBar
import com.example.cookingrecipe.viewModels.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private val args by navArgs<DetailsActivityArgs>()
    private val viewModel: MainViewModel by viewModels()

    private var recipeSaved = false
    private var savedRecipeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Create viewPager's fragments' list
        val fragments = ArrayList<Fragment>()
        fragments.add(RecipeDetailsFragment())
        fragments.add(RecipeIngredientsFragment())
        fragments.add(RecipeInstructionsFragment())

        // Create tabLayout's titles' list
        val titles = ArrayList<String>()
        titles.add("Details")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable("recipeBundle", args.result)

        val pagerAdapter = PagerAdapter(resultBundle, fragments, this)

        binding.viewPager2.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
         }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        // check if recipe has already been saved
        val menuItem = menu.findItem(R.id.add_to_favorites_menu)
        checkFavoriteRecipes(menuItem!!)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.add_to_favorites_menu && recipeSaved) {
            removeFromFavorites(item)
        } else if (item.itemId == R.id.add_to_favorites_menu) {
            addToFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addToFavorites(item: MenuItem) {
        // Add the recipe to the database
        val favoritesEntity = FavoritesEntity(0, args.result)
        viewModel.insertFavoriteRecipe(favoritesEntity)

        // Change the star icon to yellow
        changeMenuItemColor(item, R.color.yellow)

        // Show a snackBar
        showSnackBar(binding.detailsScreenLayout, "Recipe saved.")
        recipeSaved = true
    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(savedRecipeId, args.result)
        viewModel.deleteFavoriteRecipe(favoritesEntity)
        // Change the star icon to white
        changeMenuItemColor(item, R.color.white)
        recipeSaved = false
    }

    private fun checkFavoriteRecipes(menuItem: MenuItem) {
        changeMenuItemColor(menuItem, R.color.white)
        viewModel.getFavoriteRecipesFromLocal.observe(this) {
            try {
                for (item in it) {
                    if (item.result.id == args.result.id) {
                        changeMenuItemColor(menuItem, R.color.yellow)
                        savedRecipeId = item.id
                        recipeSaved = true
                    }
                }
            } catch (e: Exception) {
                Log.d("DeatailsActivity", e.message.toString())
            }
        }
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }
}