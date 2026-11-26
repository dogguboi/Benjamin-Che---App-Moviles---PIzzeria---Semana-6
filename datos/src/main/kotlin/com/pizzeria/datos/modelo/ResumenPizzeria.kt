package com.pizzeria.datos.modelo

/** Datos del resumen de la pantalla inicial. */
data class ResumenPizzeria(
    val totalClientes: Int,
    val totalPedidos: Int,
    val ultimoCliente: String?
)
