package com.example.cookingrecipe.fragment.detailScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.cookingrecipe.R
import com.example.cookingrecipe.models.Result
import com.example.cookingrecipe.utils.Constants
import kotlinx.android.synthetic.main.fragment_recipe_instructions.view.*

class RecipeInstructionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recipe_instructions, container, false)

        val args = arguments
        val resultBundle: Result? = args?.getParcelable(Constants.RESULT_BUNDLE_KEY)

        view.wv_instructions.webViewClient = object : WebViewClient() {}
        view.wv_instructions.loadUrl(resultBundle!!.sourceUrl)

        return view
    }

}