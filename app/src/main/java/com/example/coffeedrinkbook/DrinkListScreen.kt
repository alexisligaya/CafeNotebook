package com.example.coffeedrinkbook

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.coffeedrinkbook.ui.theme.DarkGreen
import com.example.coffeedrinkbook.ui.theme.SageGreen
import androidx.compose.ui.layout.ContentScale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinkListScreen(navController: NavHostController, drinkViewModel: DrinkViewModel) {
    val drinks by drinkViewModel.drinks.collectAsState()
    var selectedTabIndex by remember { mutableStateOf(0) }

    // Choose a random drink for "Drink of the Day"
    val drinkOfTheDay by drinkViewModel.randomDrinkOfTheDay.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.cafe),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Lighten image
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.4f))
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Cafe Notebook",
                                color = Color.White,
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontFamily = FontFamily.Serif
                                )
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkGreen)
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate("add_drink") },
                    containerColor = SageGreen,
                    contentColor = DarkGreen
                ) {
                    Text("+")
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Tabs
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = Color.White
                ) {
                    Tab(
                        selected = selectedTabIndex == 0,
                        onClick = { selectedTabIndex = 0 },
                        text = { Text("All Drinks", color = DarkGreen) }
                    )
                    Tab(
                        selected = selectedTabIndex == 1,
                        onClick = { selectedTabIndex = 1 },
                        text = { Text("Favorites", color = DarkGreen) }
                    )
                    Tab(
                        selected = selectedTabIndex == 2,
                        onClick = {
                            selectedTabIndex = 2
                            drinkViewModel.refreshDrinkOfTheDay()
                        },
                        text = { Text("Drink of the Day", color = DarkGreen) }
                    )
                }

                val drinksToShow = when (selectedTabIndex) {
                    0 -> drinks
                    1 -> drinks.filter { it.favorite }
                    2 -> {
                        println("💡 drinkOfTheDay value = $drinkOfTheDay")

                        // Even if it's null, show placeholder
                        listOf(
                            Drink(
                                title = drinkOfTheDay?.title ?: "No drink loaded",
                                type = drinkOfTheDay?.type ?: "Drink not loaded. Add drinks first."
                            )
                        )
                    }

                    else -> emptyList()
                }

                DrinkList(
                    drinks = drinksToShow,
                    onDeleteDrink = { drinkViewModel.removeDrink(it) },
                    onEditDrink = { oldDrink, newDrink -> drinkViewModel.editDrink(oldDrink, newDrink) },
                    onToggleFavorite = { drinkViewModel.toggleFavorite(it) },
                    modifier = Modifier.padding(8.dp),
                    showActions = selectedTabIndex != 2
                )

            }
        }
    }
}
