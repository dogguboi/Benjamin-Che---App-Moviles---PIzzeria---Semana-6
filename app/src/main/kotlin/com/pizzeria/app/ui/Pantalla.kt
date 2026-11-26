package com.pizzeria.app.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

/** Pantallas de la aplicación; cada una trae el título y el ícono que usa el menú desplegable. */
enum class Pantalla(val titulo: String, val icono: ImageVector) {
    INICIO("Inicio", Icons.Default.Home),
    CLIENTES("Clientes", Icons.Default.Person),
    PRODUCTOS("Productos", Icons.Default.ShoppingCart),
    PEDIDOS("Pedidos", Icons.Default.Add)
}
