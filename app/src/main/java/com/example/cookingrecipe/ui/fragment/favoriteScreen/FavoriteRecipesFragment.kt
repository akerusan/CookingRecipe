package com.example.cookingrecipe.ui.fragment.favoriteScreen

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookingrecipe.R
import com.example.cookingrecipe.adapters.FavoriteRecipeAdapter
import com.example.cookingrecipe.databinding.FragmentRecipesFavoriteBinding
import com.example.cookingrecipe.utils.Commons.Companion.showSnackBar
import com.example.cookingrecipe.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private val mAdapter by lazy { FavoriteRecipeAdapter(requireActivity(), viewModel) }

    private var _binding: FragmentRecipesFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecipesFavoriteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = viewModel
        binding.mAdapter = mAdapter

        // Delete menu
        setHasOptionsMenu(true)

        setUpRecyclerView()

        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.favoritesRecyclerView.adapter = mAdapter
        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_recipes_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAll_favorite_recipes_menu) {
            viewModel.deleteAllFavoriteRecipes()
            showSnackBar(binding.root, "All recipes deleted")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mAdapter.stopContextualActionMode()
        _binding = null
    }
}