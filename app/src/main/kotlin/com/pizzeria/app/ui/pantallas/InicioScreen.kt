package com.pizzeria.app.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pizzeria.app.PizzeriaViewModel
import com.pizzeria.app.ui.Pantalla

/** Pantalla inicial: mensaje de bienvenida, resumen dinámico y accesos a otras pantallas. */
@Composable
fun InicioScreen(viewModel: PizzeriaViewModel, onNavegar: (Pantalla) -> Unit) {
    val resumen = viewModel.resumen

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Melipizza: La mejor pizza de Melipilla",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Resumen", style = MaterialTheme.typography.titleMedium)
                if (resumen == null) {
                    Text("Aún no hay datos. Presiona «Actualizar resumen».")
                } else {
                    Text("Clientes registrados: ${resumen.totalClientes}")
                    Text("Pedidos realizados: ${resumen.totalPedidos}")
                    Text("Último cliente registrado: ${resumen.ultimoCliente ?: "-"}")
                }
            }
        }

        Button(
            onClick = { viewModel.cargarDatos() },
            enabled = !viewModel.cargando,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Actualizar resumen")
        }

        // Estos botones navegan a otras pantallas: la pantalla actual hace Fade Out y la nueva Fade In.
        Button(
            onClick = { onNavegar(Pantalla.PEDIDOS) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Registrar un pedido")
        }

        OutlinedButton(
            onClick = { onNavegar(Pantalla.CLIENTES) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Person, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Registrar un cliente")
        }
    }
}
