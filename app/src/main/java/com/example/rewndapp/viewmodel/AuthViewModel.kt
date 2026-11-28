package com.example.rewndapp.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rewndapp.AuthResult
import com.example.rewndapp.repositories.AuthRepository
import com.example.rewndapp.uiState.AuthUiState
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    var uiState by mutableStateOf(AuthUiState(haySesion = AuthRepository.haySesion()))
        private set

    // Iniciar sesión
    fun onLogin(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            uiState = uiState.copy(error = "Rellena todos los campos")
            return
        }

        uiState = uiState.copy(cargando = true, error = null)
        AuthRepository.login(email.trim(), password) { resultado ->
            if (resultado is AuthResult.Error) {
                uiState = uiState.copy(cargando = false, error = resultado.error)
            } else {
                uiState = uiState.copy(cargando = false, haySesion = true)
            }
        }
    }

    // Registrarse
    fun onRegistro(email: String, password: String, repetir: String) {
        if (email.isBlank() || password.isBlank() || repetir.isBlank()) {
            uiState = uiState.copy(error = "Rellena todos los campos")
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            uiState = uiState.copy(error = "El correo no es válido")
            return
        }
        if (password.length < 8) {
            uiState = uiState.copy(error = "La contraseña debe tener al menos 8 caracteres")
            return
        }
        if (password != repetir) {
            uiState = uiState.copy(error = "Las contraseñas no coinciden")
            return
        }

        uiState = uiState.copy(cargando = true, error = null)
        AuthRepository.registrar(email.trim(), password) { resultado ->
            if (resultado is AuthResult.Error) {
                uiState = uiState.copy(cargando = false, error = resultado.error)
            } else {
                uiState = uiState.copy(cargando = false, haySesion = true)
            }
        }

    }

    // Recuperar contraseña
    fun onRecuperarClave(email: String) {
        if (email.isBlank()) {
            uiState = uiState.copy(error = "Escribe tu correo")
            return
        }
        uiState = uiState.copy(cargando = true, error = null, mensaje = null)
        AuthRepository.recuperarClave(email.trim()) { resultado ->
            if (resultado is AuthResult.Error) {
                uiState = uiState.copy(cargando = false, error = resultado.error)
            } else {
                uiState = uiState.copy(
                    cargando = false,
                    mensaje = "Te hemos enviado un correo para restablecer la contraseña"
                )
            }
        }
    }

    // Cerrar sesión
    fun onCerrarSesion() {
        AuthRepository.cerrarSesion()
        uiState = AuthUiState()
    }

    fun onLimpiarError() {
        uiState = uiState.copy(error = null, mensaje = null)
    }
}