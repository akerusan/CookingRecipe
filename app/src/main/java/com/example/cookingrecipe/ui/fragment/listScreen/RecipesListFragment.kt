package com.example.cookingrecipe.ui.fragment.listScreen

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookingrecipe.viewModels.MainViewModel
import com.example.cookingrecipe.R
import com.example.cookingrecipe.adapters.RecipesAdapter
import com.example.cookingrecipe.databinding.FragmentRecipesListBinding
import com.example.cookingrecipe.models.FoodRecipe
import com.example.cookingrecipe.utils.NetworkListener
import com.example.cookingrecipe.utils.NetworkResult
import com.example.cookingrecipe.utils.observeOnce
import com.example.cookingrecipe.viewModels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesListFragment : Fragment(), SearchView.OnQueryTextListener {

    private val args by navArgs<RecipesListFragmentArgs>()

    private var _binding: FragmentRecipesListBinding? = null
    private val binding get() = _binding!!

    private val mAdapter by lazy { RecipesAdapter() }

    private lateinit var viewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel

    private lateinit var networkListener: NetworkListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesListBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.mainViewModel = viewModel

        // Search menu
        setHasOptionsMenu(true)

        setupRecyclerView()

        recipesViewModel.getBackOnline.observe(viewLifecycleOwner) {
            recipesViewModel.backOnline = it
        }

        lifecycleScope.launchWhenStarted {
            networkListener = NetworkListener()
            networkListener.checkConnection(requireContext()).collect {
                Log.d("NetworkListener", it.toString())
                recipesViewModel.hasNetwork = it
                recipesViewModel.showNetworkStatus()
                verifyDatabase()
            }
        }

        binding.sortRecipesFab.setOnClickListener {
            if (recipesViewModel.hasNetwork) {
                findNavController().navigate(R.id.action_recipesFragment_to_listBottomSheetFragment)
            } else {
                recipesViewModel.showNetworkStatus()
            }
        }

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu, menu)

        val search = menu.findItem(R.id.recipe_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(searchQuery: String?): Boolean {
        if (!searchQuery.isNullOrEmpty()) {
            searchApiData(searchQuery)
            showShimmer()
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmer()
    }

    private fun verifyDatabase() {
        lifecycleScope.launch {
            viewModel.getRecipesFromLocal.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
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
        viewModel.recipesResponse.observe(viewLifecycleOwner) {
            handleApiResponse(it)
        }
    }

    private fun searchApiData(searchQuery: String) {
        viewModel.searchRecipes(recipesViewModel.createSearchQueries(searchQuery))
        viewModel.searchResponse.observe(viewLifecycleOwner) {
            handleApiResponse(it)
        }
    }

    private fun handleApiResponse(response: NetworkResult<FoodRecipe>) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}