package com.example.cookingrecipe.data

import com.example.cookingrecipe.data.database.RecipesDao
import com.example.cookingrecipe.data.database.entities.FavoritesEntity
import com.example.cookingrecipe.data.database.entities.FoodJokeEntity
import com.example.cookingrecipe.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val recipesDao: RecipesDao) {

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    fun getRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.getRecipes()
    }

    suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity) {
        recipesDao.insertFavoriteRecipes(favoritesEntity)
    }

    fun getFavoriteRecipes(): Flow<List<FavoritesEntity>> {
        return recipesDao.getFavoriteRecipes()
    }

    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        recipesDao.deleteFavoriteRecipe(favoritesEntity)
    }

    suspend fun deleteAllFavoriteRecipes() {
        recipesDao.deleteAllFavoriteRecipes()
    }

    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
        recipesDao.insertFoodJoke(foodJokeEntity)
    }

    fun getFoodJoke(): Flow<List<FoodJokeEntity>> {
        return recipesDao.getFoodJoke()
    }
}