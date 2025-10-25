package com.example.crimewave.data.model

data class User(
    val email: String,
    val password: String,
    val phoneNumber: String? = null,
    val isAdmin: Boolean = false
)

data class AuthState(
    val isAuthenticated: Boolean = false,
    val currentUser: User? = null,
    val error: String? = null
)
