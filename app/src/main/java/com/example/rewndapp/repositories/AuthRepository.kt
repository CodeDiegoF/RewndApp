package com.example.rewndapp.repositories

import com.example.rewndapp.AuthResult

object AuthRepository {

    suspend fun login(email: String, contrasenia: String): AuthResult {}
}