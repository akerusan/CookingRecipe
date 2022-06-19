package com.example.cookingrecipe.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cookingrecipe.models.FoodRecipe
import com.example.cookingrecipe.utils.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(var foodRecipe: FoodRecipe) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}