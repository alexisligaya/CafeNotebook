package com.example.coffeedrinkbook
import android.app.Application

class DrinkViewModelFactory(private val application: Application) :
    androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DrinkViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DrinkViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
