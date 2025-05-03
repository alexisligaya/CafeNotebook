package com.example.coffeedrinkbook.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = CoffeeBrown,
    secondary = MintGreen,
    tertiary = Cream,
    background = Cream,
    surface = LightGray,
    onPrimary = Color.White,
    onSecondary = DarkBrown,
    onTertiary = DarkBrown,
    onBackground = DarkBrown,
    onSurface = DarkBrown,
)

@Composable
fun CoffeeDrinkBookTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> LightColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
