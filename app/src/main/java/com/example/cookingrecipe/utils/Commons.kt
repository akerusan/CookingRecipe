package com.example.cookingrecipe.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

class Commons {

    companion object {

        fun showSnackBar(
            view: View,
            message: String,
            length: Int = Snackbar.LENGTH_SHORT,
            action: String = "Okay"
        ) {
            Snackbar.make(view, message, length).setAction(action){}.show()
        }

    }
}