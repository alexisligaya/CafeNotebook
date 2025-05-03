package com.example.coffeedrinkbook

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.coffeedrinkbook.ui.theme.DarkGreen
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowBack
import android.widget.Toast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDrinkScreen(
    navController: NavHostController,
    drinkViewModel: DrinkViewModel
) {
    var titleText by remember { mutableStateOf(TextFieldValue("")) }
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Hot", "Iced")
    var selectedTemperature by remember { mutableStateOf("Select Temperature") }
    var espressoShots by remember { mutableStateOf(TextFieldValue("")) }
    var milkType by remember { mutableStateOf(TextFieldValue("")) }
    var syrupFlavor by remember { mutableStateOf(TextFieldValue("")) }
    var notesText by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                title = {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            "Add New Drink",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge.copy(fontFamily = FontFamily.Serif)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkGreen)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(titleText, { titleText = it }, label = { Text("Drink Name") }, modifier = Modifier.fillMaxWidth())
            Box(Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = selectedTemperature,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Drink Temperature") },
                    trailingIcon = {
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = DarkGreen)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    options.forEach {
                        DropdownMenuItem(text = { Text(it) }, onClick = {
                            selectedTemperature = it
                            expanded = false
                        })
                    }
                }
            }

            OutlinedTextField(espressoShots, { espressoShots = it }, label = { Text("Espresso Shots") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(milkType, { milkType = it }, label = { Text("Milk Type") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(syrupFlavor, { syrupFlavor = it }, label = { Text("Syrup Flavor") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(notesText, { notesText = it }, label = { Text("Notes (optional)") }, modifier = Modifier.fillMaxWidth())

            Button(
                onClick = {
                    val recipeLines = mutableListOf<String>()
                    if (selectedTemperature != "Select Temperature") recipeLines.add("Temperature: $selectedTemperature")
                    if (espressoShots.text.isNotBlank()) recipeLines.add("Espresso Shots: ${espressoShots.text}")
                    if (milkType.text.isNotBlank()) recipeLines.add("Milk: ${milkType.text}")
                    if (syrupFlavor.text.isNotBlank()) recipeLines.add("Syrup: ${syrupFlavor.text}")
                    if (notesText.text.isNotBlank()) recipeLines.add("Notes: ${notesText.text}")
                    val recipe = recipeLines.joinToString("\n")

                    drinkViewModel.addDrink(Drink(titleText.text, recipe))
                    Toast.makeText(navController.context, "Drink saved successfully!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = DarkGreen, contentColor = Color.White)
            ) {
                Text("Save Drink")
            }
        }
    }
}
