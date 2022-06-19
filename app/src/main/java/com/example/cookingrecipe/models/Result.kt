package com.example.cookingrecipe.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

// Generated by Json to kotlin class plugin
// TODO: Rename to recipe ??
@Parcelize
data class Result(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("aggregateLikes")
    val aggregateLikes: Int,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int,
    @SerializedName("vegan")
    val vegan: Boolean,
    @SerializedName("vegetarian")
    val vegetarian: Boolean,
    @SerializedName("veryHealthy")
    val veryHealthy: Boolean,
    @SerializedName("cheap")
    val cheap: Boolean,
    @SerializedName("dairyFree")
    val dairyFree: Boolean,
    @SerializedName("glutenFree")
    val glutenFree: Boolean,
    @SerializedName("sourceUrl")
    val sourceUrl: String,
    @SerializedName("extendedIngredients")
    val extendedIngredients: List<ExtendedIngredient>
//    @SerializedName("cookingMinutes")
//    val cookingMinutes: Int,
//    @SerializedName("analyzedInstructions")
//    val analyzedInstructions: @RawValue List<AnalyzedInstruction>,
//    @SerializedName("creditsText")
//    val creditsText: String,
//    @SerializedName("cuisines")
//    val cuisines: List<String>,
//    @SerializedName("diets")
//    val diets: List<String>,
//    @SerializedName("dishTypes")
//    val dishTypes: List<String>,
//    @SerializedName("gaps")
//    val gaps: String,
//    @SerializedName("healthScore")
//    val healthScore: Int,
//    @SerializedName("imageType")
//    val imageType: String,
//    @SerializedName("license")
//    val license: String,
//    @SerializedName("likes")
//    val likes: Int,
//    @SerializedName("lowFodmap")
//    val lowFodmap: Boolean,
//    @SerializedName("missedIngredientCount")
//    val missedIngredientCount: Int,
//    @SerializedName("missedIngredients")
//    val missedIngredients: @RawValue List<MissedIngredient>,
//    @SerializedName("nutrition")
//    val nutrition: @RawValue Nutrition,
//    @SerializedName("occasions")
//    val occasions: @RawValue List<Any>,
//    @SerializedName("openLicense")
//    val openLicense: Int,
//    @SerializedName("preparationMinutes")
//    val preparationMinutes: Int,
//    @SerializedName("pricePerServing")
//    val pricePerServing: Double,
//    @SerializedName("servings")
//    val servings: Int,
//    @SerializedName("sourceName")
//    val sourceName: String,
//    @SerializedName("spoonacularSourceUrl")
//    val spoonacularSourceUrl: String,
//    @SerializedName("sustainable")
//    val sustainable: Boolean,
//    @SerializedName("unusedIngredients")
//    val unusedIngredients: @RawValue List<Any>,
//    @SerializedName("usedIngredientCount")
//    val usedIngredientCount: Int,
//    @SerializedName("usedIngredients")
//    val usedIngredients: @RawValue List<Any>,
//    @SerializedName("veryPopular")
//    val veryPopular: Boolean,
//    @SerializedName("weightWatcherSmartPoints")
//    val weightWatcherSmartPoints: Int
): Parcelable