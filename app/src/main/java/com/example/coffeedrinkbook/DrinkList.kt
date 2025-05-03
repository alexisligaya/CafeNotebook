package com.example.coffeedrinkbook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrinkList(
    drinks: List<Drink>,
    onDeleteDrink: (Drink) -> Unit,
    onEditDrink: (Drink, Drink) -> Unit,
    onToggleFavorite: (Drink) -> Unit,
    modifier: Modifier = Modifier,
    showActions: Boolean = true
) {
    var drinkBeingEdited by remember { mutableStateOf<Drink?>(null) }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(drinks) { drink ->
            DrinkItem(
                drink = drink,
                onDelete = { onDeleteDrink(drink) },
                onEdit = { drinkBeingEdited = drink },
                onToggleFavorite = { onToggleFavorite(drink) },
                showActions = showActions
            )
        }
    }

    drinkBeingEdited?.let { originalDrink ->
        EditDrinkDialog(
            drink = originalDrink,
            onDismiss = { drinkBeingEdited = null },
            onConfirm = { updatedDrink ->
                onEditDrink(originalDrink, updatedDrink)
                drinkBeingEdited = null
            }
        )
    }
}
