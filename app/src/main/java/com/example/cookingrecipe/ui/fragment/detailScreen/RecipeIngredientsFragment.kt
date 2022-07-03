package com.example.cookingrecipe.ui.fragment.detailScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookingrecipe.adapters.IngredientsAdapter
import com.example.cookingrecipe.databinding.FragmentRecipeIngredientsBinding
import com.example.cookingrecipe.models.Result
import com.example.cookingrecipe.utils.Constants.Companion.RESULT_BUNDLE_KEY

class RecipeIngredientsFragment : Fragment() {

    private val adapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    private var _binding: FragmentRecipeIngredientsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeIngredientsBinding.inflate(inflater, container, false)

        val args = arguments
        val resultBundle: Result? = args?.getParcelable(RESULT_BUNDLE_KEY)

        setUpRecyclerView()
        resultBundle?.extendedIngredients?.let { adapter.setData(it) }

        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.ingredientsRecyclerView.adapter = adapter
        binding.ingredientsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}