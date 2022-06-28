package com.example.cookingrecipe.ui.fragment.foodJokeScreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.cookingrecipe.R
import com.example.cookingrecipe.databinding.FragmentFoodJokeBinding
import com.example.cookingrecipe.utils.Constants.Companion.API_KEY
import com.example.cookingrecipe.utils.NetworkResult
import com.example.cookingrecipe.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodJokeFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>()

    private var _binding: FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!

    private var foodJoke = "No Food Joke"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.jokeViewModel = viewModel

        setHasOptionsMenu(true)

        viewModel.getFoodJoke(API_KEY)
        viewModel.foodJokeResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.tvFoodJokeText.text = response.data?.text
                    if (response.data != null) foodJoke = response.data.text
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    Log.d("FoodJokeFragment", "Loading")
                }
            }
        }

        return binding.root
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            viewModel.getFoodJokeFromLocal.observe(viewLifecycleOwner) { db ->
                if (db.isNullOrEmpty()) {
                    binding.tvFoodJokeText.text = db[0].foodJoke.text
                    foodJoke = db[0].foodJoke.text
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.food_joke_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share_joke) {
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, foodJoke)
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }
        else if (item.itemId == R.id.reload_joke) {
            viewModel.getFoodJoke(API_KEY)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}