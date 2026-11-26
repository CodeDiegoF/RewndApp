package com.example.rewndapp

import com.example.rewndapp.model.AuthUser

interface AuthResult {
    data class Success(val user: AuthUser) : AuthResult
    data class Error(val error: String?) : AuthResult
}