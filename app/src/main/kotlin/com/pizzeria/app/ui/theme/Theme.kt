package com.pizzeria.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EsquemaClaro = lightColorScheme(
    primary = Color(0xFFB71C1C),
    onPrimary = Color.White,
    secondary = Color(0xFFEF6C00),
    onSecondary = Color.White
)

private val EsquemaOscuro = darkColorScheme(
    primary = Color(0xFFEF9A9A),
    onPrimary = Color(0xFF3B0A0A),
    secondary = Color(0xFFFFB74D),
    onSecondary = Color(0xFF3B1F00)
)

/** Material Theme de la pizzería: colores claros u oscuros según el sistema. */
@Composable
fun PizzeriaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) EsquemaOscuro else EsquemaClaro,
        content = content
    )
}
