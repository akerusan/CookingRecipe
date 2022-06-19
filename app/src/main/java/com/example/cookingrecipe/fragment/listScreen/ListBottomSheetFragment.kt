package com.example.cookingrecipe.fragment.listScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.cookingrecipe.R
import com.example.cookingrecipe.utils.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.cookingrecipe.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.cookingrecipe.viewModels.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.fragment_list_bottom_sheet.view.*
import java.lang.Exception

class ListBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: RecipesViewModel

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeIdChip = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeIdChip = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initializing View Model
        viewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mView =  inflater.inflate(R.layout.fragment_list_bottom_sheet, container, false)

        viewModel.getMealAndDietType.asLiveData().observe(viewLifecycleOwner) {
            mealTypeChip = it.selectedMealType
            dietTypeChip = it.selectedDietType

            updateChip(it.selectedMealTypeId, mView.meal_type_chip_group)
            updateChip(it.selectedDietTypeId, mView.diet_type_chip_group)
        }

        // Get selected meal type and it's id
        mView.meal_type_chip_group.setOnCheckedChangeListener { group, selectedId ->
            val chip = group.findViewById<Chip>(selectedId)
            mealTypeChip = chip.text.toString().lowercase()
            mealTypeIdChip = selectedId
        }

        // Get selected diet type and it's id
        mView.diet_type_chip_group.setOnCheckedChangeListener { group, selectedId ->
            val chip = group.findViewById<Chip>(selectedId)
            dietTypeChip = chip.text.toString().lowercase()
            dietTypeIdChip = selectedId
        }

        // Store the selected meal and diet type ans their IDs
        mView.bottom_sheet_apply_button.setOnClickListener {
            viewModel.saveMealAndDietType(mealTypeChip, mealTypeIdChip, dietTypeChip, dietTypeIdChip)

            val action = ListBottomSheetFragmentDirections.actionListBottomSheetFragmentToRecipesFragment(true)
            findNavController().navigate(action)
        }

        return mView
    }

    private fun updateChip(id: Int, group: ChipGroup) {
        if (id != 0) {
            try {
                group.findViewById<Chip>(id).isChecked = true
            } catch (e: Exception) {
                Log.d("BottomSheetError", e.message.toString())
            }
        }
    }
}