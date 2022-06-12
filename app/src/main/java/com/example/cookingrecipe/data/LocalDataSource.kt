package com.example.cookingrecipe.data

import com.example.cookingrecipe.data.database.RecipesDao
import com.example.cookingrecipe.data.database.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val recipesDao: RecipesDao) {

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    fun getRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.getRecipes()
    }
}