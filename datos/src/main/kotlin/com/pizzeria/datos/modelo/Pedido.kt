package com.pizzeria.datos.modelo

import com.pizzeria.utilidades.Validaciones

/**
 * Pedido de un [cliente] con sus [productos] (producto -> cantidad).
 * Usa el módulo de utilidades para validar las cantidades (dependencia :datos -> :utilidades).
 */
class Pedido(
    val id: String,
    val cliente: Cliente,
    val productos: Map<Producto, Int>
) {
    init {
        require(productos.isNotEmpty()) { "El pedido $id debe tener al menos un producto." }
        productos.forEach { (producto, cantidad) ->
            require(Validaciones.cantidadValida(cantidad)) {
                "Cantidad inválida para '${producto.nombre}' en el pedido $id: $cantidad " +
                    "(permitido: ${Validaciones.RANGO_CANTIDAD})."
            }
        }
    }

    val subtotal: Int get() = Precios.subtotal(productos)
    val descuento: Int get() = Precios.descuento(productos)
    val total: Int get() = Precios.total(productos)
}
