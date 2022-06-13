package com.example.cookingrecipe.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookingrecipe.viewModels.MainViewModel
import com.example.cookingrecipe.R
import com.example.cookingrecipe.adapters.RecipesAdapter
import com.example.cookingrecipe.databinding.FragmentRecipesBinding
import com.example.cookingrecipe.utils.NetworkResult
import com.example.cookingrecipe.utils.observeOnce
import com.example.cookingrecipe.viewModels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private val mAdapter by lazy { RecipesAdapter() }

    private lateinit var viewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = viewModel

        setupRecyclerView()

        verifyDatabase()

        binding.sortRecipesFab.setOnClickListener {
            findNavController().navigate(R.id.action_recipesFragment_to_listBottomSheetFragment)
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmer()
    }

    private fun verifyDatabase() {
        lifecycleScope.launch {
            viewModel.getRecipesFromLocal.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmer()
                } else {
                    requestApiData()
                }
            }
        }
    }

    private fun requestApiData() {
        viewModel.getRecipesFromRemote(recipesViewModel.createQueries())
        viewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmer()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    hideShimmer()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT)
                }
                is NetworkResult.Loading -> {
                    showShimmer()
                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            viewModel.getRecipesFromLocal.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].foodRecipe)
                }
            }
        }
    }

    private fun showShimmer() {
        binding.recyclerView.showShimmer()
    }

    private fun hideShimmer() {
        binding.recyclerView.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}