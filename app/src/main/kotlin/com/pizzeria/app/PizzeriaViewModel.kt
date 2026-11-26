package com.pizzeria.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pizzeria.datos.modelo.Cliente
import com.pizzeria.datos.modelo.Pedido
import com.pizzeria.datos.modelo.Producto
import com.pizzeria.datos.modelo.ResumenPizzeria
import com.pizzeria.datos.repositorio.RepositorioPizzeria
import com.pizzeria.utilidades.Formato
import kotlinx.coroutines.launch

/**
 * ViewModel: conecta la lógica del módulo de datos con las pantallas de Compose.
 *
 * Expone su información como State de Compose (mutableStateOf): cuando un valor cambia,
 * las pantallas que lo leen se redibujan solas. Así los indicadores de progreso y el resumen
 * se muestran y se actualizan dinámicamente.
 */
class PizzeriaViewModel : ViewModel() {

    private val repositorio = RepositorioPizzeria()

    var productos by mutableStateOf<List<Producto>>(emptyList())
        private set
    var clientes by mutableStateOf<List<Cliente>>(emptyList())
        private set
    var pedidos by mutableStateOf<List<Pedido>>(emptyList())
        private set
    var resumen by mutableStateOf<ResumenPizzeria?>(null)
        private set

    /** true mientras se ejecuta una operación; la interfaz muestra el indicador de progreso. */
    var cargando by mutableStateOf(false)
        private set

    /** Mensaje que acompaña al indicador: "Cargando datos...", "Generando resumen...", etc. */
    var mensajeCarga by mutableStateOf("")
        private set

    /** Aviso para el usuario (se muestra en un Snackbar y luego se limpia). */
    var mensajeUsuario by mutableStateOf<String?>(null)
        private set

    init {
        cargarDatos()
    }

    fun mensajeMostrado() {
        mensajeUsuario = null
    }

    fun cargarDatos() = conProgreso("Cargando datos...") { refrescar() }

    fun registrarCliente(cliente: Cliente) = conProgreso("Guardando cliente...") {
        if (repositorio.registrarCliente(cliente)) {
            mensajeUsuario = "Cliente ${cliente.nombre} registrado"
            refrescar()
        } else {
            mensajeUsuario = "Ese cliente ya existe (mismo nombre y correo)"
        }
    }

    fun registrarPedido(cliente: Cliente, items: Map<Producto, Int>) = conProgreso("Registrando pedido...") {
        val pedido = Pedido("P-%03d".format(pedidos.size + 1), cliente, items)
        repositorio.registrarPedido(pedido)
        mensajeUsuario = "Pedido ${pedido.id} registrado por ${Formato.clp(pedido.total)}"
        refrescar()
    }

    /** Vuelve a leer los datos desde el módulo de datos y regenera el resumen. */
    private suspend fun refrescar() {
        productos = repositorio.obtenerProductos()
        clientes = repositorio.obtenerClientes()
        pedidos = repositorio.obtenerPedidos()
        mensajeCarga = "Generando resumen..."
        resumen = repositorio.generarResumen()
    }

    /** Ejecuta [bloque] mostrando el progreso; ignora la orden si ya hay otra operación en curso. */
    private fun conProgreso(mensaje: String, bloque: suspend () -> Unit) {
        if (cargando) return
        viewModelScope.launch {
            cargando = true
            mensajeCarga = mensaje
            try {
                bloque()
            } finally {
                cargando = false
            }
        }
    }
}
