package com.example.cookingrecipe.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cookingrecipe.models.Result
import com.example.cookingrecipe.utils.Constants.Companion.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)

