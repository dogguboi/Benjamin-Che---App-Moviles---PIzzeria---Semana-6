package com.pizzeria.datos.modelo

/**
 * Cliente de la pizzería.
 * Dos clientes son iguales si coinciden nombre y correo (sin distinguir mayúsculas).
 */
data class Cliente(
    val nombre: String,
    val correo: String,
    val telefono: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Cliente) return false
        return nombre.trim().equals(other.nombre.trim(), ignoreCase = true) &&
            correo.trim().equals(other.correo.trim(), ignoreCase = true)
    }

    override fun hashCode(): Int =
        31 * nombre.trim().lowercase().hashCode() + correo.trim().lowercase().hashCode()
}
