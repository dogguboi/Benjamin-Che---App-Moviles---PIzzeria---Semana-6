package com.pizzeria.utilidades

import java.util.Locale

/** Utilidades de formato para mostrar datos en pantalla. */
object Formato {
    private val LOCALE_CL: Locale = Locale.forLanguageTag("es-CL")

    /** 12990 -> $12.990 */
    fun clp(valor: Int): String = "\$" + String.format(LOCALE_CL, "%,d", valor)
}
