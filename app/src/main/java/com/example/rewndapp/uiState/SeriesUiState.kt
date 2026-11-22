package com.example.rewndapp.uiState

import com.example.rewndapp.model.Estado
import com.example.rewndapp.model.Serie

data class SeriesUiState(
    val seriesList: List<Serie> = emptyList(),
    val estadoSerie: Estado = Estado.POR_VER,
    val errorEdicion: String? = null,
    val textoFiltro: String = "",
    val textoBusqueda: String = "",
    val resultados: List<Serie> = emptyList(),
    val serieEncontrada: Serie? = null,
    val mensaje: String? = null
){
    val seriesFiltradas: List<Serie>
        get() =
            seriesList.filter { it.estado == estadoSerie &&
                    it.titulo.contains(textoFiltro, ignoreCase = true) }
}

