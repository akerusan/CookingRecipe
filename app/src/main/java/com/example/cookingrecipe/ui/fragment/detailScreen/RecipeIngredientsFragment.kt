package com.example.cookingrecipe.ui.fragment.detailScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookingrecipe.R
import com.example.cookingrecipe.adapters.IngredientsAdapter
import com.example.cookingrecipe.models.Result
import com.example.cookingrecipe.utils.Constants.Companion.RESULT_BUNDLE_KEY
import kotlinx.android.synthetic.main.fragment_recipe_ingredients.view.*

class RecipeIngredientsFragment : Fragment() {

    private val adapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false)

        val args = arguments
        val resultBundle: Result? = args?.getParcelable(RESULT_BUNDLE_KEY)

        setUpRecyclerView(view)
        resultBundle?.extendedIngredients?.let { adapter.setData(it) }

        return view
    }

    private fun setUpRecyclerView(view: View) {
        view.ingredients_recycler_view.adapter = adapter
        view.ingredients_recycler_view.layoutManager = LinearLayoutManager(requireContext())
    }

}