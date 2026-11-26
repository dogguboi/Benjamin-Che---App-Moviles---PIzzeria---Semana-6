package com.pizzeria.app.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pizzeria.app.PizzeriaViewModel
import com.pizzeria.app.ui.componentes.ProgresoCarga
import com.pizzeria.app.ui.pantallas.ClientesScreen
import com.pizzeria.app.ui.pantallas.InicioScreen
import com.pizzeria.app.ui.pantallas.PedidosScreen
import com.pizzeria.app.ui.pantallas.ProductosScreen

/**
 * Raíz de la aplicación. Integra los tres elementos de la semana 6:
 *  1. Animaciones Fade In/Out con AnimatedVisibility (al iniciar y al cambiar de pantalla).
 *  2. Menú desplegable (DropdownMenu) con íconos, en la barra superior.
 *  3. Notificación de progreso mientras el ViewModel carga datos del módulo de datos.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PizzeriaApp(viewModel: PizzeriaViewModel = viewModel()) {
    var pantallaActual by remember { mutableStateOf(Pantalla.INICIO) }
    var menuAbierto by remember { mutableStateOf(false) }
    var aplicacionVisible by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Fade In al iniciar: la pantalla principal parte invisible y aparece gradualmente.
    LaunchedEffect(Unit) { aplicacionVisible = true }

    // Muestra los avisos del ViewModel (cliente registrado, pedido registrado, etc.).
    LaunchedEffect(viewModel.mensajeUsuario) {
        viewModel.mensajeUsuario?.let { mensaje ->
            snackbarHostState.showSnackbar(mensaje)
            viewModel.mensajeMostrado()
        }
    }

    AnimatedVisibility(
        visible = aplicacionVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(pantallaActual.titulo) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    actions = {
                        Box {
                            IconButton(onClick = { menuAbierto = true }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "Abrir menú")
                            }
                            DropdownMenu(
                                expanded = menuAbierto,
                                onDismissRequest = { menuAbierto = false }
                            ) {
                                Pantalla.entries.forEach { destino ->
                                    DropdownMenuItem(
                                        text = { Text(destino.titulo) },
                                        leadingIcon = { Icon(destino.icono, contentDescription = null) },
                                        onClick = {
                                            menuAbierto = false
                                            pantallaActual = destino
                                        }
                                    )
                                }
                            }
                        }
                    }
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { paddingInterno ->
            Column(modifier = Modifier.fillMaxSize().padding(paddingInterno)) {

                // Notificación de progreso (se ve en cualquier pantalla mientras hay una operación).
                ProgresoCarga(visible = viewModel.cargando, mensaje = viewModel.mensajeCarga)

                // Cada pantalla se muestra con AnimatedVisibility: la que sale hace Fade Out
                // y la que entra hace Fade In (ver PantallasAnimadas, más abajo).
                PantallasAnimadas(
                    pantallaActual = pantallaActual,
                    viewModel = viewModel,
                    onNavegar = { pantallaActual = it },
                    modifier = Modifier.fillMaxWidth().weight(1f)
                )
            }
        }
    }
}

/**
 * Muestra la pantalla actual con Fade In / Fade Out usando AnimatedVisibility.
 *
 * Está en su propia función (y no dentro del Column de PizzeriaApp) a propósito: así no hay un
 * ColumnScope implícito y Kotlin usa la versión general de AnimatedVisibility.
 */
@Composable
private fun PantallasAnimadas(
    pantallaActual: Pantalla,
    viewModel: PizzeriaViewModel,
    onNavegar: (Pantalla) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Pantalla.entries.forEach { destino ->
            AnimatedVisibility(
                visible = pantallaActual == destino,
                enter = fadeIn(animationSpec = tween(durationMillis = 600)),
                exit = fadeOut(animationSpec = tween(durationMillis = 400))
            ) {
                when (destino) {
                    Pantalla.INICIO -> InicioScreen(viewModel = viewModel, onNavegar = onNavegar)
                    Pantalla.CLIENTES -> ClientesScreen(viewModel)
                    Pantalla.PRODUCTOS -> ProductosScreen(viewModel)
                    Pantalla.PEDIDOS -> PedidosScreen(viewModel)
                }
            }
        }
    }
}