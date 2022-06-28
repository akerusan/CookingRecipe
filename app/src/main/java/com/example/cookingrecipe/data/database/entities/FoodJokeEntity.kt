package com.example.cookingrecipe.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cookingrecipe.models.FoodJoke
import com.example.cookingrecipe.utils.Constants

@Entity(tableName = Constants.FOOD_JOKE_TABLE)
class FoodJokeEntity(@Embedded var foodJoke: FoodJoke) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}