package com.example.cookingrecipe.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.cookingrecipe.R
import com.example.cookingrecipe.adapters.PagerAdapter
import com.example.cookingrecipe.fragment.detailScreen.RecipeDetailsFragment
import com.example.cookingrecipe.fragment.detailScreen.RecipeIngredientsFragment
import com.example.cookingrecipe.fragment.detailScreen.RecipeInstructionsFragment
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
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

        val adapter = PagerAdapter(
            resultBundle,
            fragments,
            titles,
            supportFragmentManager
        )

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}