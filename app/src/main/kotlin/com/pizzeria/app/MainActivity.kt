package com.pizzeria.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pizzeria.app.ui.PizzeriaApp
import com.pizzeria.app.ui.theme.PizzeriaTheme

/** Punto de entrada: aplica el tema y muestra la aplicación completa de Compose. */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PizzeriaTheme {
                PizzeriaApp()
            }
        }
    }
}
