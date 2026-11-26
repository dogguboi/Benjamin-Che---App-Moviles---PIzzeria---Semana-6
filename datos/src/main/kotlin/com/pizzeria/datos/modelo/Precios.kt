package com.pizzeria.datos.modelo

/**
 * Cálculo de costos de un pedido (lógica de negocio, sin depender de la interfaz).
 * Regla: 10% de descuento si el pedido tiene más de 3 pizzas.
 */
object Precios {
    const val PIZZAS_PARA_DESCUENTO = 3
    const val PORCENTAJE_DESCUENTO = 10

    fun cantidadPizzas(items: Map<Producto, Int>): Int =
        items.filterKeys { it.categoria == Categoria.PIZZA }.values.sum()

    fun subtotal(items: Map<Producto, Int>): Int =
        items.entries.sumOf { (producto, cantidad) -> producto.precio * cantidad }

    fun descuento(items: Map<Producto, Int>): Int =
        if (cantidadPizzas(items) > PIZZAS_PARA_DESCUENTO) subtotal(items) * PORCENTAJE_DESCUENTO / 100 else 0

    fun total(items: Map<Producto, Int>): Int = subtotal(items) - descuento(items)
}
