package com.example.crimewave.data.model

data class User(
    val email: String,
    val password: String,
    val phoneNumber: String? = null,
    val isAdmin: Boolean = false,
    val shippingAddress: ShippingAddress? = null,
    val billingAddress: BillingAddress? = null
)

data class ShippingAddress(
    val nombre: String,
    val apellidos: String,
    val direccion: String,
    val rut: String,
    val ciudad: String,
    val codigoPostal: String,
    val pais: String,
    val region: String,
    val celular: String,
    val instagram: String
)

data class BillingAddress(
    val nombre: String,
    val apellidos: String,
    val direccion: String,
    val rut: String,
    val ciudad: String,
    val codigoPostal: String,
    val pais: String,
    val region: String,
    val celular: String,
    val instagram: String
)

data class AuthState(
    val isAuthenticated: Boolean = false,
    val currentUser: User? = null,
    val error: String? = null
)
