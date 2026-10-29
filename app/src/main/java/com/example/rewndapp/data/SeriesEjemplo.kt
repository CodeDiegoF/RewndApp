package com.example.rewndapp.data

import com.example.rewndapp.model.Estado
import com.example.rewndapp.model.Serie

// Lista de ejemplo para las primeras versiones
val listaSeries = listOf(
    Serie(
        titulo = "Dark",
        anio = "2017",
        generos = "Misterio, ciencia ficción",
        progreso = "T1 terminada - T2 en curso",
        pais = "2017–2020 · Alemania",
        temporadas = "3 temporadas · 26 episodios",
        idioma = "Alemán · aprox. 60 min",
        sinopsis = "La desaparición de un niño lleva a cuatro familias de un pequeño pueblo alemán a descubrir un misterio que atraviesa generaciones.",
        estado = Estado.VIENDO
    ),
    Serie(
        titulo = "Severance",
        anio = "2022",
        generos = "Drama, ciencia ficción",
        progreso = "T1 en curso",
        pais = "2022 · Estados Unidos",
        temporadas = "2 temporadas · 19 episodios",
        idioma = "Inglés · aprox. 50 min",
        sinopsis = "Los empleados de una empresa se someten a un procedimiento que separa sus recuerdos del trabajo de los de su vida personal.",
        estado = Estado.VIENDO
    )
)