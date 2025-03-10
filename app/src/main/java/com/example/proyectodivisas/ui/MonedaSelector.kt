package com.example.proyectodivisas.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MonedaSelector(monedaSeleccionada: String, onMonedaSeleccionada: (String) -> Unit) {
    val monedas = listOf("USD", "EUR", "GBP", "JPY") // Lista de monedas

    DropdownMenu(
        expanded = false, // Cambia esto para controlar la visibilidad del menú
        onDismissRequest = { /* Cierra el menú */ }
    ) {
        monedas.forEach { moneda ->
            DropdownMenuItem(
                onClick = { onMonedaSeleccionada(moneda) },
                text = { Text(text = moneda) }
            )
        }
    }
}