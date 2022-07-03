package com.example.cookingrecipe.ui.fragment.detailScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.cookingrecipe.databinding.FragmentRecipeInstructionsBinding
import com.example.cookingrecipe.models.Result
import com.example.cookingrecipe.utils.Constants

class RecipeInstructionsFragment : Fragment() {

    private var _binding: FragmentRecipeInstructionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeInstructionsBinding.inflate(inflater, container, false)

        val args = arguments
        val resultBundle: Result? = args?.getParcelable(Constants.RESULT_BUNDLE_KEY)

        binding.wvInstructions.webViewClient = object : WebViewClient() {}
        binding.wvInstructions.loadUrl(resultBundle!!.sourceUrl)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}