package com.example.cookingrecipe.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

// To avoid calling back the database after fetching data from the API
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object: Observer<T> {
        override fun onChanged(t: T) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}