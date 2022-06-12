package com.example.cookingrecipe.utils


// https://kotlinlang.org/docs/sealed-classes.html
sealed class NetworkResult<T>(val data: T? = null, val message: String? = null) {

    class Loading<T>: NetworkResult<T>()
    class Success<T>(data: T?): NetworkResult<T>(data)
    class Error<T>(data: T? = null, message: String?): NetworkResult<T>(data, message)
}

// TODO*: verify difference with above code
//sealed class NetworkResult<out T : Any>() {
//
//    object Loading : Result<Nothing>()
//    data class Success<out T : Any>(val data: T?): Result<T>()
//    data class Error<out T : Any>(val message: String?, val data: T? = null): Result<T>()
//}