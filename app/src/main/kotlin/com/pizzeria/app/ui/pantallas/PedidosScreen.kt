package com.pizzeria.app.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pizzeria.app.PizzeriaViewModel
import com.pizzeria.datos.modelo.Cliente
import com.pizzeria.datos.modelo.Precios
import com.pizzeria.datos.modelo.Producto
import com.pizzeria.utilidades.Formato
import com.pizzeria.utilidades.Validaciones

/** Registro de pedidos (cliente + productos con cantidades) y listado dinámico. */
@Composable
fun PedidosScreen(viewModel: PizzeriaViewModel) {
    var clienteElegido by remember { mutableStateOf<Cliente?>(null) }
    var menuClientes by remember { mutableStateOf(false) }
    val cantidades = remember { mutableStateMapOf<Producto, Int>() }

    // Productos con al menos 1 unidad y su costo, calculado con la lógica del módulo de datos.
    val seleccion = cantidades.filterValues { it > 0 }
    val subtotal = Precios.subtotal(seleccion)
    val descuento = Precios.descuento(seleccion)
    val total = Precios.total(seleccion)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item { Text("Nuevo pedido", style = MaterialTheme.typography.titleLarge) }

        item {
            Box {
                OutlinedButton(
                    onClick = { menuClientes = true },
                    enabled = viewModel.clientes.isNotEmpty()
                ) {
                    Icon(Icons.Default.Person, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(clienteElegido?.nombre ?: "Seleccionar cliente")
                }
                DropdownMenu(expanded = menuClientes, onDismissRequest = { menuClientes = false }) {
                    viewModel.clientes.forEach { cliente ->
                        DropdownMenuItem(
                            text = { Text(cliente.nombre) },
                            onClick = {
                                clienteElegido = cliente
                                menuClientes = false
                            }
                        )
                    }
                }
            }
        }
        if (viewModel.clientes.isEmpty()) {
            item { Text("Primero registra un cliente en la sección Clientes.") }
        }

        items(viewModel.productos) { producto ->
            FilaProducto(
                producto = producto,
                cantidad = cantidades[producto] ?: 0,
                onCambiar = { nueva -> cantidades[producto] = nueva }
            )
        }

        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("Subtotal: ${Formato.clp(subtotal)}")
                    Text("Descuento por volumen (más de ${Precios.PIZZAS_PARA_DESCUENTO} pizzas): -${Formato.clp(descuento)}")
                    Text(
                        "Total: ${Formato.clp(total)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item {
            Button(
                onClick = {
                    val cliente = clienteElegido
                    if (cliente != null && seleccion.isNotEmpty()) {
                        viewModel.registrarPedido(cliente, seleccion)
                        cantidades.clear()
                    }
                },
                enabled = clienteElegido != null && seleccion.isNotEmpty() && !viewModel.cargando,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar pedido")
            }
        }

        item { HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) }
        item {
            Text(
                "Pedidos realizados (${viewModel.pedidos.size})",
                style = MaterialTheme.typography.titleLarge
            )
        }
        items(viewModel.pedidos.reversed()) { pedido ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("${pedido.id} - ${pedido.cliente.nombre}", fontWeight = FontWeight.Bold)
                    pedido.productos.forEach { (producto, cantidad) ->
                        Text("• ${producto.nombre} x $cantidad", style = MaterialTheme.typography.bodyMedium)
                    }
                    Text(
                        "Total: ${Formato.clp(pedido.total)}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

/** Una fila del pedido: nombre, precio y botones para restar o sumar unidades (0 a 100). */
@Composable
private fun FilaProducto(producto: Producto, cantidad: Int, onCambiar: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(producto.nombre, style = MaterialTheme.typography.bodyLarge)
            Text(Formato.clp(producto.precio), style = MaterialTheme.typography.bodySmall)
        }
        OutlinedButton(
            onClick = { if (cantidad > 0) onCambiar(cantidad - 1) },
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.size(40.dp)
        ) {
            Text("-")
        }
        Text(
            text = cantidad.toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier.width(36.dp)
        )
        OutlinedButton(
            onClick = { if (cantidad < Validaciones.RANGO_CANTIDAD.last) onCambiar(cantidad + 1) },
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.size(40.dp)
        ) {
            Text("+")
        }
    }
}
