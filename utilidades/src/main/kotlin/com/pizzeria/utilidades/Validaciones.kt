package com.pizzeria.utilidades

/**
 * Validaciones reutilizables de la pizzería (módulo de utilidades).
 * Usan expresiones regulares (Regex) y rangos (Ranges), igual que en la semana 3.
 */
object Validaciones {

    /** Cantidad permitida por producto: de 1 a 100 unidades. */
    val RANGO_CANTIDAD: IntRange = 1..100

    // nombre@dominio.ext
    private val REGEX_CORREO =
        Regex("""^[\w.+-]+@[A-Za-z0-9-]+(\.[A-Za-z0-9-]+)*\.[A-Za-z]{2,}$""")

    // Caracteres permitidos al escribir un teléfono: dígitos, espacios, + ( ) . -
    private val REGEX_CARACTERES_TELEFONO = Regex("""^\+?[\d\s().-]+$""")

    fun esCorreoValido(correo: String): Boolean = REGEX_CORREO.matches(correo.trim())

    fun cantidadValida(cantidad: Int): Boolean = cantidad in RANGO_CANTIDAD

    /**
     * Formatea un móvil chileno como +56 9 XXXX XXXX.
     * Acepta 9 dígitos (9XXXXXXXX, se asume [codigoPais]) u 11 dígitos (56 + 9 dígitos).
     * Devuelve null si el texto no es un teléfono válido.
     */
    fun formatearTelefono(entrada: String, codigoPais: String = "56"): String? {
        val texto = entrada.trim()
        if (!REGEX_CARACTERES_TELEFONO.matches(texto)) return null

        val digitos = texto.filter { it.isDigit() }
        val nacional = when (digitos.length) {
            9 -> digitos
            11 -> if (digitos.startsWith(codigoPais)) digitos.removePrefix(codigoPais) else return null
            else -> return null
        }
        if (!nacional.startsWith("9")) return null

        return "+$codigoPais ${nacional[0]} ${nacional.substring(1, 5)} ${nacional.substring(5)}"
    }
}
