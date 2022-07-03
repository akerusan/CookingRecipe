package com.example.cookingrecipe.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExtendedIngredient(
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("consistency")
    val consistency: String,
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("original")
    val original: String,
    @SerializedName("unit")
    val unit: String
//    @SerializedName("aisle")
//    val aisle: String,
//    @SerializedName("id")
//    val id: Int,
//    @SerializedName("originalName")
//    val originalName: String,
//    @SerializedName("nameClean")
//    val nameClean: String,
//    @SerializedName("measures")
//    val measures: @RawValue Measures,
//    @SerializedName("meta")
//    val meta: List<String>,
): Parcelable