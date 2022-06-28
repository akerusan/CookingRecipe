package com.example.cookingrecipe.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cookingrecipe.data.database.entities.FavoritesEntity
import com.example.cookingrecipe.data.database.entities.FoodJokeEntity
import com.example.cookingrecipe.data.database.entities.RecipesEntity

@TypeConverters(RecipesTypeConverter::class)
@Database(
    entities = [RecipesEntity::class, FavoritesEntity::class, FoodJokeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RecipesDatabase : RoomDatabase() {

    abstract fun recipesDao(): RecipesDao
}