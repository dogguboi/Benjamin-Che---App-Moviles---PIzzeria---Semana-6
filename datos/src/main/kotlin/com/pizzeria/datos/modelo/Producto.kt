package com.pizzeria.datos.modelo

/**
 * Producto que vende la pizzería.
 * Dos productos son iguales si tienen el mismo nombre (sin distinguir mayúsculas) y categoría.
 * Esto permite usarlo como clave de un Map (producto -> cantidad) sin duplicados.
 */
class Producto(
    val nombre: String,
    val categoria: Categoria,
    val precio: Int,
    val ingredientes: List<String> = emptyList()
) {
    init {
        require(nombre.isNotBlank()) { "El nombre del producto no puede estar vacío." }
        require(precio > 0) { "El precio de '$nombre' debe ser mayor que cero." }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Producto) return false
        return nombre.trim().equals(other.nombre.trim(), ignoreCase = true) &&
            categoria == other.categoria
    }

    override fun hashCode(): Int =
        31 * nombre.trim().lowercase().hashCode() + categoria.hashCode()

    override fun toString(): String = "Producto(nombre=$nombre, categoria=$categoria, precio=$precio)"
}
