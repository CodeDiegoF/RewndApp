package com.example.rewndapp.model

import java.util.UUID

data class Serie(
    val id: String = UUID.randomUUID().toString(),
    val titulo: String,
    val anio: Int,
    val generos: String,
    val pais: String,
    val totalTemporadas: Int,
    val episodios: Int,
    val idioma: String,
    val sinopsis: String,
    val imagen: String,
    val estado: Estado,
    val temporadasTerminadas: Int = 0,
    val temporadaEnCurso: Int = 0
) {
    val progreso: String
        get() {
            if (totalTemporadas > 0 && temporadasTerminadas == totalTemporadas) {
                return "Completada"
            }
            if (temporadasTerminadas == 0 && temporadaEnCurso == 0) {
                return "Sin empezar"
            }
            var texto = ""
            if (temporadasTerminadas > 0) {
                texto = "T$temporadasTerminadas terminada"
            }
            if (temporadaEnCurso > 0) {
                if (texto != "") {
                    texto += " - "
                }
                texto += "T$temporadaEnCurso en curso"
            }
            return texto
        }
}