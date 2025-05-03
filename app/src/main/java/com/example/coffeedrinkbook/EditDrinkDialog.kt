package com.example.coffeedrinkbook

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.coffeedrinkbook.ui.theme.DarkGreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown

@Composable
fun EditDrinkDialog(
    drink: Drink,
    onDismiss: () -> Unit,
    onConfirm: (Drink) -> Unit
) {
    val lines = drink.type.split("\n")
    val temp = lines.find { it.startsWith("Temperature: ") }?.substringAfter("Temperature: ") ?: ""
    val shots = lines.find { it.startsWith("Espresso Shots: ") }?.substringAfter("Espresso Shots: ") ?: ""
    val milk = lines.find { it.startsWith("Milk: ") }?.substringAfter("Milk: ") ?: ""
    val syrup = lines.find { it.startsWith("Syrup: ") }?.substringAfter("Syrup: ") ?: ""
    val notes = lines.find { it.startsWith("Notes: ") }?.substringAfter("Notes: ") ?: ""

    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Hot", "Iced")

    var title by remember { mutableStateOf(TextFieldValue(drink.title)) }
    var temperature by remember { mutableStateOf(temp) }
    var espresso by remember { mutableStateOf(TextFieldValue(shots)) }
    var milkType by remember { mutableStateOf(TextFieldValue(milk)) }
    var syrupFlavor by remember { mutableStateOf(TextFieldValue(syrup)) }
    var notesText by remember { mutableStateOf(TextFieldValue(notes)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Drink") },
        confirmButton = {
            Button(onClick = {
                val result = buildString {
                    if (temperature.isNotBlank()) append("Temperature: $temperature\n")
                    if (espresso.text.isNotBlank()) append("Espresso Shots: ${espresso.text}\n")
                    if (milkType.text.isNotBlank()) append("Milk: ${milkType.text}\n")
                    if (syrupFlavor.text.isNotBlank()) append("Syrup: ${syrupFlavor.text}\n")
                    if (notesText.text.isNotBlank()) append("Notes: ${notesText.text}\n")
                }.trim()

                onConfirm(
                    Drink(
                        title = title.text,
                        type = result,
                        favorite = drink.favorite
                    )
                )
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Drink Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Box(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = temperature,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Temperature") },
                        trailingIcon = {
                            IconButton(onClick = { expanded = true }) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = "Expand",
                                    tint = DarkGreen
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        options.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    temperature = it
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = espresso,
                    onValueChange = { espresso = it },
                    label = { Text("Espresso Shots") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = milkType,
                    onValueChange = { milkType = it },
                    label = { Text("Milk Type") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = syrupFlavor,
                    onValueChange = { syrupFlavor = it },
                    label = { Text("Syrup Flavor") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = notesText,
                    onValueChange = { notesText = it },
                    label = { Text("Notes (optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}
