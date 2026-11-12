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

    // Lista y detalle
    fun onSerieClick(serie: Serie) {
        uiState = uiState.copy(serieIdSeleccionado = serie.id)
    }

    fun onVolver() {
        uiState = uiState.copy(serieIdSeleccionado = null)
    }

    // Borrado
    fun onBorrarClick(serie: Serie) {
        uiState = uiState.copy(serieABorrar = serie)
    }

    fun onCancelarBorrado(serie: Serie) {
        uiState = uiState.copy(serieABorrar = null)
    }

    fun onConfirmarBorrado(){
        val serie = uiState.serieABorrar ?: return
        SerieRepository.deleteSerie(serie.id)
        uiState = uiState.copy(
            seriesList = SerieRepository.getSeries(),
            serieABorrar = null,
            serieIdSeleccionado = null,
            mensaje = "Serie '${serie.titulo}' eliminada"
        )
    }

    // Añadir Serie
    fun onAnadirClick() {
        uiState = uiState.copy(mostrarAnadir = true)
    }

    fun onCancelarAnadir() {
        uiState = uiState.copy(
            mostrarAnadir = false,
            textoBusqueda = "",
            resultados = emptyList(),
            serieEncontrada = null
        )
    }

    fun onBusqueda(texto: String){
        uiState = uiState.copy(
            textoBusqueda = texto,
            resultados = SerieRepository.buscarSeries(texto),
            serieEncontrada = null
        )
    }

    fun onResultadoClick(serie: Serie) {
        uiState = uiState.copy(serieEncontrada = serie,)

    }

    fun onConfirmarAnadir(){
        val serie = uiState.serieEncontrada ?: return
        SerieRepository.addSerie(serie)
        uiState = uiState.copy(
            seriesList = SerieRepository.getSeries(),
            mostrarAnadir = false,
            textoBusqueda = "",
            resultados = emptyList(),
            serieEncontrada = null,
            mensaje = "Serie '${serie.titulo}' añadida a Por ver"
        )
    }

    // Editar Serie
    fun onEditarClick(serie: Serie) {
        uiState = uiState.copy(serieAEditar = serie)
    }

    fun onCancelarEdicion() {
        uiState = uiState.copy(
            serieAEditar = null,
            errorEdicion = null)
    }

    fun onGuardarEdicion(estado: Estado, temporada: Int, terminada: Boolean) {
        val serie = uiState.serieAEditar ?: return
        var terminadas = serie.temporadasTerminadas
        var enCurso = serie.temporadaEnCurso

        if (estado == Estado.POR_VER) {
            terminadas = 0
            enCurso = 0
        } else if (terminada) {
            terminadas = temporada
            if (enCurso <= temporada) {
                enCurso = 0
            }
        } else {
            terminadas = temporada - 1
            enCurso = temporada
        }

        if (estado == Estado.COMPLETADA && terminadas != serie.totalTemporadas) {
            uiState = uiState.copy(
                errorEdicion = "Para marcarla como Completadas, termina la última temporada (T${serie.totalTemporadas})"
            )
            return
        }
        if (estado == Estado.VIENDO && terminadas == serie.totalTemporadas) {
            uiState = uiState.copy(
                errorEdicion = "Has terminado todas las temporadas: el estado debe ser Completadas"
            )
            return
        }

        val serieActualizada = serie.copy(
            estado = estado,
            temporadasTerminadas = terminadas,
            temporadaEnCurso = enCurso
        )
        SerieRepository.updateSerie(serieActualizada)
        uiState = uiState.copy(
            seriesList = SerieRepository.getSeries(),
            serieAEditar = null,
            errorEdicion = null,
            mensaje = "Progreso de '${serie.titulo}' actualizado"
        )
    }

    // Mensajes
    fun onMensajeMostrado() {
        uiState = uiState.copy(mensaje = null)
    }

}