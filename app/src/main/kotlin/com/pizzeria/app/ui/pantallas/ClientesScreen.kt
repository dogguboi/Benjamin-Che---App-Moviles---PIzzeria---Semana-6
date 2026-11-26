package com.pizzeria.app.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.pizzeria.app.PizzeriaViewModel
import com.pizzeria.datos.modelo.Cliente
import com.pizzeria.utilidades.Validaciones

/** Registro de clientes (con validaciones del módulo de utilidades) y listado dinámico. */
@Composable
fun ClientesScreen(viewModel: PizzeriaViewModel) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var intentoDeEnvio by remember { mutableStateOf(false) }

    val telefonoFormateado = Validaciones.formatearTelefono(telefono)
    val errorNombre = intentoDeEnvio && nombre.isBlank()
    val errorCorreo = intentoDeEnvio && !Validaciones.esCorreoValido(correo)
    val errorTelefono = intentoDeEnvio && telefonoFormateado == null

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item { Text("Registrar cliente", style = MaterialTheme.typography.titleLarge) }

        item {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                singleLine = true,
                isError = errorNombre,
                supportingText = { if (errorNombre) Text("El nombre no puede estar vacío") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = errorCorreo,
                supportingText = { if (errorCorreo) Text("Formato inválido. Ejemplo: nombre@dominio.com") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono móvil") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = errorTelefono,
                supportingText = {
                    Text(
                        if (errorTelefono) "Teléfono inválido. Use un móvil chileno, ej: 9 1234 5678"
                        else "Ejemplo: 9 1234 5678"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Button(
                onClick = {
                    intentoDeEnvio = true
                    val datosValidos = nombre.isNotBlank() &&
                        Validaciones.esCorreoValido(correo) &&
                        telefonoFormateado != null
                    if (datosValidos && telefonoFormateado != null) {
                        viewModel.registrarCliente(Cliente(nombre.trim(), correo.trim(), telefonoFormateado))
                        nombre = ""
                        correo = ""
                        telefono = ""
                        intentoDeEnvio = false
                    }
                },
                enabled = !viewModel.cargando,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar cliente")
            }
        }

        item { HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp)) }
        item {
            Text(
                "Clientes registrados (${viewModel.clientes.size})",
                style = MaterialTheme.typography.titleLarge
            )
        }
        items(viewModel.clientes) { cliente ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(cliente.nombre, fontWeight = FontWeight.Bold)
                    Text(cliente.correo, style = MaterialTheme.typography.bodyMedium)
                    Text(cliente.telefono, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
