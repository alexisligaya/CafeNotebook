package com.example.coffeedrinkbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.coffeedrinkbook.ui.theme.CoffeeDrinkBookTheme
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val drinkViewModel: DrinkViewModel = viewModel(
                factory = DrinkViewModelFactory(application)
            )

            CoffeeDrinkBookTheme {
                DrinkApp(drinkViewModel)
            }
        }
    }
}

