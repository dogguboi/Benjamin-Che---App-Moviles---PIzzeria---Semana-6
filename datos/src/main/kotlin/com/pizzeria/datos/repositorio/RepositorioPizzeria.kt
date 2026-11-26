package com.pizzeria.datos.repositorio

import com.pizzeria.datos.modelo.Categoria
import com.pizzeria.datos.modelo.Cliente
import com.pizzeria.datos.modelo.Pedido
import com.pizzeria.datos.modelo.Producto
import com.pizzeria.datos.modelo.ResumenPizzeria
import kotlinx.coroutines.delay

/**
 * Repositorio en memoria de la pizzería (módulo de datos).
 *
 * Las funciones son `suspend` y esperan unos instantes (delay) para simular una carga real
 * (base de datos o red). Gracias a eso la interfaz puede mostrar los indicadores de progreso
 * mientras los datos se cargan.
 */
class RepositorioPizzeria(private val retardoMs: Long = 900) {

    private val clientes = mutableListOf(
        Cliente("Juanito Pérez", "juan.perez@yahoo.com", "+56 9 1234 5678"),
        Cliente("Enrique Fuentealba", "kike@gmail.cl", "+56 9 8765 4321")
    )

    private val catalogo = listOf(
        Producto("Cuatro Quesos", Categoria.PIZZA, 12990, listOf("mozzarella", "gorgonzola", "parmesano", "provolone")),
        Producto("Pepperoni Especial", Categoria.PIZZA, 11990, listOf("mozzarella", "pepperoni")),
        Producto("Pizza Margarita", Categoria.PIZZA, 8990, listOf("mozzarella", "tomate", "albahaca")),
        Producto("Combo Familiar", Categoria.PIZZA, 24990, listOf("2 pizzas medianas", "bebida 1.5 L")),
        Producto("Bebida 1.5 L", Categoria.BEBIDA, 2500),
        Producto("Queso Extra", Categoria.INGREDIENTE, 1500)
    )

    private val pedidos = mutableListOf(
        Pedido("P-001", clientes[0], mapOf(catalogo[0] to 2, catalogo[4] to 1))
    )

    suspend fun obtenerProductos(): List<Producto> {
        delay(retardoMs)
        return catalogo.toList()
    }

    suspend fun obtenerClientes(): List<Cliente> {
        delay(retardoMs)
        return clientes.toList()
    }

    suspend fun obtenerPedidos(): List<Pedido> {
        delay(retardoMs)
        return pedidos.toList()
    }

    /** Devuelve false si ya existe un cliente igual (mismo nombre y correo). */
    suspend fun registrarCliente(cliente: Cliente): Boolean {
        delay(retardoMs)
        if (cliente in clientes) return false
        clientes.add(cliente)
        return true
    }

    suspend fun registrarPedido(pedido: Pedido) {
        delay(retardoMs)
        pedidos.add(pedido)
    }

    /** Total de clientes, total de pedidos y nombre del último cliente registrado. */
    suspend fun generarResumen(): ResumenPizzeria {
        delay(retardoMs)
        return ResumenPizzeria(
            totalClientes = clientes.size,
            totalPedidos = pedidos.size,
            ultimoCliente = clientes.lastOrNull()?.nombre
        )
    }
}
