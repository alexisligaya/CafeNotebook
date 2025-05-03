package com.example.coffeedrinkbook

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun DrinkApp(drinkViewModel: DrinkViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "drink_list") {
        composable("drink_list") {
            DrinkListScreen(
                navController = navController,
                drinkViewModel = drinkViewModel
            )
        }
        composable("add_drink") {
            AddDrinkScreen(
                navController = navController,
                drinkViewModel = drinkViewModel
            )
        }
    }
}
