package com.example.rewndapp.uiState

import com.example.rewndapp.model.Serie

data class SeriesUiState(
    val seriesList: List<Serie> = emptyList(),
    val errorEdicion: String? = null,
    val textoBusqueda: String = "",
    val resultados: List<Serie> = emptyList(),
    val serieEncontrada: Serie? = null,
    val mensaje: String? = null
)

