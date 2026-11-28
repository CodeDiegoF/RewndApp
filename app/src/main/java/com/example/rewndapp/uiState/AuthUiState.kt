package com.example.rewndapp.uiState

data class AuthUiState(
    val cargando: Boolean = false,
    val error: String? = null,
    val mensaje: String? = null,
    val haySesion: Boolean = false
)
