package com.pizzeria.app.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pizzeria.app.PizzeriaViewModel
import com.pizzeria.utilidades.Formato

/** Catálogo de productos cargado desde el módulo de datos. */
@Composable
fun ProductosScreen(viewModel: PizzeriaViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                "Productos (${viewModel.productos.size})",
                style = MaterialTheme.typography.titleLarge
            )
        }
        items(viewModel.productos) { producto ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(producto.nombre, fontWeight = FontWeight.Bold)
                        Text(Formato.clp(producto.precio), fontWeight = FontWeight.Bold)
                    }
                    Text(producto.categoria.etiqueta, style = MaterialTheme.typography.bodyMedium)
                    if (producto.ingredientes.isNotEmpty()) {
                        Text(
                            "Ingredientes: ${producto.ingredientes.joinToString(", ")}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}
