package com.example.rewndapp.model

import java.util.UUID
import kotlin.String

data class AuthUser(
    val uid: String = UUID.randomUUID().toString(),
    val email: String?,
)
