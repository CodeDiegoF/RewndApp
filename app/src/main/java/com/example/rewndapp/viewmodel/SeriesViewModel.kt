package com.example.rewndapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.rewndapp.model.Estado
import com.example.rewndapp.model.Serie
import com.example.rewndapp.repositories.SerieRepository
import com.example.rewndapp.uiState.SeriesUiState

class SeriesViewModel : ViewModel() {
    var uiState by mutableStateOf(SeriesUiState())
        private set

    init {
        uiState = uiState.copy(seriesList = SerieRepository.getSeries())
    }

    // Borrar Serie
    fun onConfirmarBorrado(id: String){
        val serie = uiState.seriesList.find { it.id == id } ?: return
        SerieRepository.deleteSerie(serie.id)
        uiState = uiState.copy(
            seriesList = SerieRepository.getSeries(),
            mensaje = "Serie '${serie.titulo}' eliminada"
        )
    }

    // Buscar Serie
    fun onBusqueda(texto: String){
        uiState = uiState.copy(
            textoBusqueda = texto,
            resultados = SerieRepository.buscarSeries(texto),
            serieEncontrada = null
        )
    }

    // Elegir un resultado de la búsqueda
    fun onResultadoClick(serie: Serie) {
        uiState = uiState.copy(serieEncontrada = serie)
    }

    // Confirmación para añadir serie
    fun onConfirmarAnadir(){
        val serie = uiState.serieEncontrada ?: return
        SerieRepository.addSerie(serie)
        uiState = uiState.copy(
            seriesList = SerieRepository.getSeries(),
            textoBusqueda = "",
            resultados = emptyList(),
            serieEncontrada = null,
            mensaje = "Serie '${serie.titulo}' añadida a Por ver"
        )
    }

    // Editar Serie
    fun onCancelarEdicion() {
        uiState = uiState.copy(errorEdicion = null)
    }

    // Guardar Serie
    fun onGuardarEdicion(estado: Estado, temporada: Int, terminada: Boolean, id: String): Boolean {
        val serie = uiState.seriesList.find{it.id == id} ?: return false
        var terminadas = serie.temporadasTerminadas
        var enCurso = serie.temporadaEnCurso

        if (estado == Estado.POR_VER) {
            terminadas = 0
            enCurso = 0
            return true
        } else if (terminada) {
            terminadas = temporada
            if (enCurso <= temporada) {
                enCurso = 0
                return true
            }
        } else {
            terminadas = temporada - 1
            enCurso = temporada
            return true
        }

        if (estado == Estado.COMPLETADA && terminadas != serie.totalTemporadas) {
            uiState = uiState.copy(
                errorEdicion = "Para marcarla como Completadas, termina la última temporada (T${serie.totalTemporadas})"
            )
            return false
        }
        if (estado == Estado.VIENDO && terminadas == serie.totalTemporadas) {
            uiState = uiState.copy(
                errorEdicion = "Has terminado todas las temporadas: el estado debe ser Completadas"
            )
            return false
        }

        val serieActualizada = serie.copy(
            estado = estado,
            temporadasTerminadas = terminadas,
            temporadaEnCurso = enCurso
        )
        SerieRepository.updateSerie(serieActualizada)
        uiState = uiState.copy(
            seriesList = SerieRepository.getSeries(),
            errorEdicion = null,
            mensaje = "Progreso de '${serie.titulo}' actualizado"
        )
        return true
    }

    // Filtrar Serie
    fun onFiltrarSeries(estado: Estado){
        uiState = uiState.copy(estadoSerie = estado)
    }

    // Mensajes
    fun onMensajeMostrado() {
        uiState = uiState.copy(mensaje = null)
    }

}