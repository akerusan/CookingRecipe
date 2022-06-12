package com.example.cookingrecipe.data.database

import androidx.room.TypeConverter
import com.example.cookingrecipe.models.FoodRecipe
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
        val listType = object : TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(data, listType)
    }

}