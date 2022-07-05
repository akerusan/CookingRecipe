package com.example.cookingrecipe.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.cookingrecipe.data.Repository
import com.example.cookingrecipe.data.database.entities.FavoritesEntity
import com.example.cookingrecipe.data.database.entities.FoodJokeEntity
import com.example.cookingrecipe.data.database.entities.RecipesEntity
import com.example.cookingrecipe.models.FoodJoke
import com.example.cookingrecipe.models.FoodRecipe
import com.example.cookingrecipe.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /** FROM LOCAL DB */

    val getRecipesFromLocal: LiveData<List<RecipesEntity>> = repository.local.getRecipes().asLiveData()
    val getFavoriteRecipesFromLocal: LiveData<List<FavoritesEntity>> = repository.local.getFavoriteRecipes().asLiveData()
    val getFoodJokeFromLocal: LiveData<List<FoodJokeEntity>> = repository.local.getFoodJoke().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }

    fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavoriteRecipes(favoritesEntity)
        }

    fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFoodJoke(foodJokeEntity)
        }

    fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavoriteRecipe(favoritesEntity)
        }

    fun deleteAllFavoriteRecipes() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllFavoriteRecipes()
        }

    /** FROM REMOTE */

    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var searchResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    val foodJokeResponse: MutableLiveData<NetworkResult<FoodJoke>> = MutableLiveData()

    fun getRecipesFromRemote(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun searchRecipes(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQuery)
    }

    fun getFoodJoke(apiKey: String) = viewModelScope.launch {
        getFoodJokeSafeCall(apiKey)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleRecipesResponse(response)

                val foodRecipe = recipesResponse.value!!.data
                if (foodRecipe != null) offlineCacheRecipe(foodRecipe)

            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error(message = "Recipes not found.")
            }
        } else {
            recipesResponse.value = NetworkResult.Error(message = "No internet connection.")
        }
    }

    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        searchResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.searchRecipes(searchQuery)
                searchResponse.value = handleRecipesResponse(response)
            } catch (e: Exception) {
                searchResponse.value = NetworkResult.Error(message = "Recipes not found.")
            }
        } else {
            searchResponse.value = NetworkResult.Error(message = "No internet connection.")
        }
    }

    private suspend fun getFoodJokeSafeCall(apiKey: String) {
        foodJokeResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getFoodJoke(apiKey)
                foodJokeResponse.value = handleFoodJokeResponse(response)

                val foodJoke = foodJokeResponse.value!!.data
                if (foodJoke != null) offlineCacheFoodJoke(foodJoke)

            } catch (e: Exception) {
                foodJokeResponse.value = NetworkResult.Error(message = "Food Joke not found.")
            }
        } else {
            foodJokeResponse.value = NetworkResult.Error(message = "No internet connection.")
        }
    }

    // Store API's results in Database
    private fun offlineCacheRecipe(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }

    // Store API's results in Database
    private fun offlineCacheFoodJoke(foodJoke: FoodJoke) {
        val foodJokeEntity = FoodJokeEntity(foodJoke)
        insertFoodJoke(foodJokeEntity)
    }

    // Check the response from the API
    private fun handleRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe> {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error(message = "Timeout")
            }
            response.code() == 402 -> {
                NetworkResult.Error(message = "Api Key Limited.")
            }
            response.body()?.results.isNullOrEmpty() -> {
                NetworkResult.Error(message = "Recipes not found.")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                NetworkResult.Success(foodRecipes)
            }
            else -> {
                NetworkResult.Error(message = response.message())
            }
        }
    }

    // Check the response from the API
    private fun handleFoodJokeResponse(response: Response<FoodJoke>): NetworkResult<FoodJoke> {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error(message = "Timeout")
            }
            response.code() == 402 -> {
                NetworkResult.Error(message = "Api Key Limited.")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                NetworkResult.Success(foodRecipes)
            }
            else -> {
                NetworkResult.Error(message = response.message())
            }
        }
    }

    // Check internet connection capacities
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

    }
}
