package com.example.rewndapp.routes

object Rutas {
    const val AUTH = "auth"

    const val LOGIN = "login"
    const val REGISTRO = "registro"
    const val RECUPERAR_CLAVE = "recuperar_clave"

    const val BIBLIOTECA = "biblioteca"
    const val DETALLE = "detalle/{id}"
    const val ANADIR = "anadir"
    const val ELIMINAR = "eliminar/{id}"
    const val EDITAR = "editar/{id}"

    fun detalle(id: String) = "detalle/$id"
    fun eliminar(id: String) = "eliminar/$id"
    fun editar(id: String) = "editar/$id"
}