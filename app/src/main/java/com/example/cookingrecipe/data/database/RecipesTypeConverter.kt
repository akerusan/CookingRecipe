package com.example.cookingrecipe.data.database

import androidx.room.TypeConverter
import com.example.cookingrecipe.models.FoodRecipe
import com.example.cookingrecipe.models.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun recipeToString(recipe: FoodRecipe): String {
        return gson.toJson(recipe)
    }

    @TypeConverter
    fun stringToRecipe(data: String): FoodRecipe {
        val listType = object : TypeToken<FoodRecipe>(){}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun resultToString(result: Result): String {
        return gson.toJson(result)
    }

    @TypeConverter
    fun stringToResult(data: String): Result {
        val listType = object : TypeToken<Result>(){}.type
        return gson.fromJson(data, listType)
    }

}