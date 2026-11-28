package com.example.rewndapp.repositories

import com.example.rewndapp.AuthResult
import com.example.rewndapp.model.AuthUser
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth

object AuthRepository {

    private val auth = Firebase.auth

    fun login(email: String, password: String, onResultado: (AuthResult) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val usuario = auth.currentUser
                    onResultado(AuthResult.Success(AuthUser(usuario?.uid ?: "", usuario?.email)))
                } else {
                    onResultado(AuthResult.Error(mensajeError(task.exception)))
                }
            }
    }

    fun registrar(email: String, password: String, onResultado: (AuthResult) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val usuario = auth.currentUser
                    onResultado(AuthResult.Success(AuthUser(usuario?.uid ?: "", usuario?.email)))
                } else {
                    onResultado(AuthResult.Error(mensajeError(task.exception)))
                }
            }
    }

    fun recuperarClave(email: String, onResultado: (AuthResult) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResultado(AuthResult.Enviado("Correo de recuperación enviado"))
                } else {
                    onResultado(AuthResult.Error(mensajeError(task.exception)))
                }
            }
    }

    fun cerrarSesion() {
        auth.signOut()
    }

    fun haySesion(): Boolean {
        return auth.currentUser != null
    }

    private fun mensajeError(e: Exception?): String {
        return when (e) {
            is FirebaseAuthInvalidCredentialsException -> "Correo o contraseña incorrectos"
            is FirebaseAuthInvalidUserException -> "No existe ninguna cuenta con ese correo"
            is FirebaseAuthUserCollisionException -> "Ya existe una cuenta con ese correo"
            is FirebaseAuthWeakPasswordException -> "La contraseña debe tener al menos 8 caracteres"
            else -> "Error de conexión, inténtalo de nuevo"
        }
    }
}