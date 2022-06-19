package com.example.cookingrecipe.ui.fragment.detailScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.example.cookingrecipe.R
import com.example.cookingrecipe.models.Result
import com.example.cookingrecipe.utils.Constants.Companion.RESULT_BUNDLE_KEY
import kotlinx.android.synthetic.main.fragment_recipe_details.view.*
import org.jsoup.Jsoup

class RecipeDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recipe_details, container, false)

        val args = arguments
        val resultBundle: Result? = args?.getParcelable(RESULT_BUNDLE_KEY)

        view.iv_recipe_picture.load(resultBundle?.image)
        view.tv_recipe_name.text = resultBundle?.title
        view.tv_likes.text = resultBundle?.aggregateLikes.toString()
        view.tv_time.text = resultBundle?.readyInMinutes.toString()
        resultBundle?.summary.let {
            view.tv_summary.text = Jsoup.parse(it).text()
        }

        if (resultBundle?.vegetarian == true) {
            view.iv_check_vegetarian.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.tv_vegetarian.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (resultBundle?.vegan == true) {
            view.iv_check_vegan.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.tv_vegan.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (resultBundle?.glutenFree == true) {
            view.iv_check_gluten_free.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.tv_gluten_free.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (resultBundle?.dairyFree == true) {
            view.iv_check_dairy_free.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.tv_diary_free.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (resultBundle?.veryHealthy == true) {
            view.iv_check_healthy.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.tv_healthy.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (resultBundle?.cheap == true) {
            view.iv_check_cheap.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.tv_cheap.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        return view
    }

}