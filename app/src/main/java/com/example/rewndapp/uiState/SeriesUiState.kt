package com.example.rewndapp.uiState

import com.example.rewndapp.model.Serie

data class SeriesUiState(
    val seriesList: List<Serie> = emptyList(),
    val serieIdSeleccionado: String? = null,
    val serieABorrar: Serie? = null,
    val mostrarAnadir: Boolean = false,
    val serieAEditar: Serie? = null,
    val errorEdicion: String? = null,
    val textoBusqueda: String = "",
    val resultados: List<Serie> = emptyList(),
    val serieEncontrada: Serie? = null,
    val mensaje: String? = null
)

