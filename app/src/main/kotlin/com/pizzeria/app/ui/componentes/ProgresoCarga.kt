package com.pizzeria.app.ui.componentes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Notificación de progreso: barra lineal + indicador circular + mensaje personalizado.
 * Aparece y desaparece con Fade In/Out según [visible] (que viene del State del ViewModel).
 */
@Composable
fun ProgresoCarga(visible: Boolean, mensaje: String) {
    AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 3.dp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = mensaje, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
