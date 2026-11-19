package com.example.rewndapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.rewndapp.model.Serie
import com.example.rewndapp.ui.screens.Biblioteca
import com.example.rewndapp.ui.screens.Detalle

@Composable
fun RewndApp() {
    var serieSeleccionada by remember { mutableStateOf<Serie?>(null) }
    val serie = serieSeleccionada

    if (serie == null) {
        Biblioteca(onSerieClick = { serieSeleccionada = it })
    } else {
        Detalle(
            serie = serie,
            onVolver = { serieSeleccionada = null }
        )
    }
}