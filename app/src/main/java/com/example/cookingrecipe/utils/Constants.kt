package com.example.cookingrecipe.utils

class Constants {

    companion object {

        const val BASE_URL = "https://api.spoonacular.com"
        const val API_KEY = "d1ccb03f5cb043e5ad1998d7c014a510"

        // API queries
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        //DB
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"
    }

}