package com.example.rewndapp.repositories

import com.example.rewndapp.model.Estado
import com.example.rewndapp.model.Serie

object SerieRepository {
    private val series = mutableListOf(
        Serie(
            titulo = "Dark",
            anio = 2017,
            generos = "Misterio, ciencia ficción",
            pais = "Alemania",
            totalTemporadas = 3,
            episodios = 26,
            idioma = "Alemán",
            sinopsis = "La desaparición de un niño lleva a cuatro familias de un pequeño pueblo alemán a descubrir un misterio que atraviesa generaciones.",
            imagen = "https://image.tmdb.org/t/p/w500/apbrbWs8M9lyOpJYU5WXrpFbk1Z.jpg",
            estado = Estado.VIENDO,
            temporadasTerminadas = 1,
            temporadaEnCurso = 2
        ),
        Serie(
            titulo = "Severance",
            anio = 2022,
            generos = "Drama, ciencia ficción",
            pais = "Estados Unidos",
            totalTemporadas = 2,
            episodios = 19,
            idioma = "Inglés",
            sinopsis = "Los empleados de una empresa se someten a un procedimiento que separa sus recuerdos del trabajo de los de su vida personal.",
            imagen = "https://image.tmdb.org/t/p/w500/lFf6LLrQqEBthJryj7B0Qy9e6uK.jpg",
            estado = Estado.VIENDO,
            temporadasTerminadas = 0,
            temporadaEnCurso = 1
        ),
        Serie(
            titulo = "Attack on Titan",
            anio = 2013,
            generos = "Anime, acción, drama",
            pais = "Japón",
            totalTemporadas = 4,
            episodios = 89,
            idioma = "Japonés",
            sinopsis = "La humanidad lucha por sobrevivir tras los muros frente a unos gigantescos titanes.",
            imagen = "https://image.tmdb.org/t/p/w500/hTP1DtLGFamjfu8WqjnuQdP1n4i.jpg",
            estado = Estado.COMPLETADA,
            temporadasTerminadas = 4,
            temporadaEnCurso = 0
        ),
        Serie(
            titulo = "Bárbaros",
            anio = 2020,
            generos = "Drama histórico, acción",
            pais = "Alemania",
            totalTemporadas = 2,
            episodios = 12,
            idioma = "Alemán",
            sinopsis = "Tres guerreros unen sus destinos en la batalla que enfrentará a las tribus germanas contra Roma.",
            imagen = "https://image.tmdb.org/t/p/w500/9s0a2q3fQhQ6Fqj8V8Y7uJ5w4xK.jpg",
            estado = Estado.VIENDO,
            temporadasTerminadas = 0,
            temporadaEnCurso = 1
        ),
        Serie(
            titulo = "From",
            anio = 2022,
            generos = "Terror, misterio, ciencia ficción",
            pais = "Estados Unidos",
            totalTemporadas = 3,
            episodios = 30,
            idioma = "Inglés",
            sinopsis = "Los habitantes de un pueblo misterioso buscan una salida mientras sobreviven a criaturas nocturnas.",
            imagen = "https://image.tmdb.org/t/p/w500/2wq7r9e6f4a8v3x1c5z0m6n2b7k.jpg",
            estado = Estado.VIENDO,
            temporadasTerminadas = 1,
            temporadaEnCurso = 2
        ),
        Serie(
            titulo = "The Bear",
            anio = 2022,
            generos = "Drama, comedia",
            pais = "Estados Unidos",
            totalTemporadas = 3,
            episodios = 28,
            idioma = "Inglés",
            sinopsis = "Un joven chef regresa a Chicago para hacerse cargo del restaurante familiar y transformarlo junto a su equipo.",
            imagen = "https://image.tmdb.org/t/p/w500/sHFlbKS3WLqDym8gAPm2fhp5i3x.jpg",
            estado = Estado.POR_VER
        ),
        Serie(
            titulo = "The Last of Us",
            anio = 2023,
            generos = "Drama, supervivencia",
            pais = "Estados Unidos",
            totalTemporadas = 1,
            episodios = 9,
            idioma = "Inglés",
            sinopsis = "Joel y Ellie atraviesan una América devastada para encontrar una posible cura.",
            imagen = "https://image.tmdb.org/t/p/w500/uKvVjHNqB5VmOrdxqAt2F7J78ED.jpg",
            estado = Estado.POR_VER
        )
    )

    fun getSeries(): List<Serie> {
        return series.toList()
    }

    fun addSerie(serie: Serie) {
        series.add(serie)
    }

    fun updateSerie(serie: Serie) {
        val posicion = series.indexOfFirst { it.id == serie.id }
        if (posicion != -1) {
            series[posicion] = serie
        }
    }

    fun deleteSerie(id: String) {
        series.removeAll { it.id == id }
    }

    fun buscarSeries(texto: String): List<Serie> {
        if (texto.isBlank()) {
            return emptyList()
        }
        return series.filter { serie ->
            serie.titulo.contains(texto, ignoreCase = true) && series.none { it.id == serie.id }

        }
    }
}