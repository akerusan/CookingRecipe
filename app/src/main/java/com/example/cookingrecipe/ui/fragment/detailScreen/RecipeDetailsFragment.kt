package com.example.cookingrecipe.ui.fragment.detailScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.example.cookingrecipe.R
import com.example.cookingrecipe.databinding.FragmentRecipeDetailsBinding
import com.example.cookingrecipe.models.Result
import com.example.cookingrecipe.utils.Constants.Companion.RESULT_BUNDLE_KEY
import org.jsoup.Jsoup

class RecipeDetailsFragment : Fragment() {

    private var _binding: FragmentRecipeDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)

        val args = arguments
        val resultBundle: Result? = args?.getParcelable(RESULT_BUNDLE_KEY)

        binding.ivRecipePicture.load(resultBundle?.image)
        binding.tvRecipeName.text = resultBundle?.title
        binding.tvLikes.text = resultBundle?.aggregateLikes.toString()
        binding.tvTime.text = resultBundle?.readyInMinutes.toString()
        resultBundle?.summary.let {
            binding.tvSummary.text = Jsoup.parse(it).text()
        }

        if (resultBundle?.vegetarian == true) {
            binding.ivCheckVegetarian.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.tvVegetarian.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (resultBundle?.vegan == true) {
            binding.ivCheckVegan.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.tvVegan.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (resultBundle?.glutenFree == true) {
            binding.ivCheckGlutenFree.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.tvGlutenFree.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (resultBundle?.dairyFree == true) {
            binding.ivCheckDairyFree.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.tvDairyFree.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (resultBundle?.veryHealthy == true) {
            binding.ivCheckHealthy.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.tvHealthy.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (resultBundle?.cheap == true) {
            binding.ivCheckCheap.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.tvCheap.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}