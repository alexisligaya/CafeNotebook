package com.example.coffeedrinkbook

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DrinkViewModel(application: Application) : AndroidViewModel(application) {

    private val drinkRepository = DrinkRepository(application)

    private val _drinks = MutableStateFlow<List<Drink>>(emptyList())
    val drinks: StateFlow<List<Drink>> = _drinks

    private val _randomDrinkOfTheDay = MutableStateFlow<Drink?>(null)
    val randomDrinkOfTheDay: StateFlow<Drink?> = _randomDrinkOfTheDay

    init {
        viewModelScope.launch {
            drinkRepository.drinks.map { list ->
                list.mapNotNull { entry ->
                    val parts = entry.split("::")
                    if (parts.size >= 2) {
                        val title = parts[0]
                        val type = parts[1]
                        val favorite = parts.getOrNull(2)?.toBoolean() ?: false
                        Drink(title, type, favorite)
                    } else null
                }
            }.collect { savedDrinks ->
                _drinks.value = savedDrinks
                if (savedDrinks.isNotEmpty()) {
                    _randomDrinkOfTheDay.value = savedDrinks.random()
                }
            }
        }
    }

    fun addDrink(drink: Drink) {
        viewModelScope.launch {
            drinkRepository.addDrink(drink)
        }
    }

    fun removeDrink(drink: Drink) {
        viewModelScope.launch {
            drinkRepository.removeDrink(drink)
        }
    }

    fun clearDrinks() {
        viewModelScope.launch {
            drinkRepository.clearDrinks()
        }
    }

    fun editDrink(oldDrink: Drink, newDrink: Drink) {
        viewModelScope.launch {
            drinkRepository.removeDrink(oldDrink)
            drinkRepository.addDrink(newDrink)
        }
    }

    fun toggleFavorite(drink: Drink) {
        val updatedDrink = drink.copy(favorite = !drink.favorite)
        viewModelScope.launch {
            drinkRepository.removeDrink(drink)
            drinkRepository.addDrink(updatedDrink)
        }
    }

    fun refreshDrinkOfTheDay() {
        val currentDrinks = _drinks.value
        if (currentDrinks.isNotEmpty()) {
            _randomDrinkOfTheDay.value = currentDrinks.random()
        }
    }

}
