package com.example.cookingrecipe.viewModels

import android.app.Application
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.cookingrecipe.data.DataStoreRepository
import com.example.cookingrecipe.utils.Constants.Companion.API_KEY
import com.example.cookingrecipe.utils.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.cookingrecipe.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.cookingrecipe.utils.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.example.cookingrecipe.utils.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.cookingrecipe.utils.Constants.Companion.QUERY_API_KEY
import com.example.cookingrecipe.utils.Constants.Companion.QUERY_DIET
import com.example.cookingrecipe.utils.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.cookingrecipe.utils.Constants.Companion.QUERY_NUMBER
import com.example.cookingrecipe.utils.Constants.Companion.QUERY_TYPE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RecipesViewModel @ViewModelInject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    var hasNetwork = false
    var backOnline = false

    val getMealAndDietType = dataStoreRepository.getMealAndDietType
    val getBackOnline = dataStoreRepository.getBackOnline.asLiveData()

    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }

    fun saveBackOnline(isBackOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(isBackOnline)
        }

    fun createQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            getMealAndDietType.collect {
                mealType = it.selectedMealType
                dietType = it.selectedDietType
            }
        }

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    fun showNetworkStatus() {
        if (!hasNetwork) {
            Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if (hasNetwork && backOnline) {
            Toast.makeText(getApplication(), "App is back connection", Toast.LENGTH_SHORT).show()
            saveBackOnline(false)
        }
    }
}