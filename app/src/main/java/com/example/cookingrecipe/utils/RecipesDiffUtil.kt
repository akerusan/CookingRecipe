package com.example.cookingrecipe.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.cookingrecipe.models.Result

/*
* DiffUtil
* It can be used to calculate updates for a RecyclerView Adapter.
* See ListAdapter and AsyncListDiffer which can simplify the use of DiffUtil on a background thread.
 */
// TODO: Search how to use AsyncListDiffer
class RecipesDiffUtil(private val oldList: List<Result>, private val newList: List<Result>): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}