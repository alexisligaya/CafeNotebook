package com.example.coffeedrinkbook

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "drinks")

class DrinkRepository(private val context: Context) {

    private val DRINKS_KEY = stringSetPreferencesKey("drinks_key")

    val drinks: Flow<List<String>> = context.dataStore.data.map { preferences ->
        preferences[DRINKS_KEY]?.toList() ?: emptyList()
    }

    suspend fun addDrink(drink: Drink) {
        context.dataStore.edit { preferences ->
            val currentDrinks = preferences[DRINKS_KEY] ?: emptySet()
            preferences[DRINKS_KEY] = currentDrinks + "${drink.title}::${drink.type}::${drink.favorite}"
        }
    }

    suspend fun removeDrink(drink: Drink) {
        context.dataStore.edit { preferences ->
            val currentDrinks = preferences[DRINKS_KEY]?.toMutableList() ?: mutableListOf()
            currentDrinks.remove("${drink.title}::${drink.type}::${drink.favorite}") // <-- include favorite!
            preferences[DRINKS_KEY] = currentDrinks.toSet()
        }
    }

    suspend fun clearDrinks() {
        context.dataStore.edit { preferences ->
            preferences[DRINKS_KEY] = emptySet()
        }
    }
}
