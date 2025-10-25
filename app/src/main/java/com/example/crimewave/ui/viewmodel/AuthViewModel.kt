package com.example.crimewave.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.example.crimewave.data.model.User
import com.example.crimewave.data.model.AuthState

class AuthViewModel : ViewModel() {
    private val _authState = mutableStateOf(AuthState())
    val authState: State<AuthState> = _authState

    // Lista de usuarios registrados (en una app real esto estaría en una base de datos)
    private val _registeredUsers = mutableStateOf<List<User>>(
        listOf(
            User(
                email = "admin",
                password = "admin",
                isAdmin = true
            )
        )
    )

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authState.value = _authState.value.copy(error = "Por favor complete todos los campos")
            return
        }

        val user = _registeredUsers.value.find {
            it.email == email && it.password == password
        }

        if (user != null) {
            _authState.value = AuthState(
                isAuthenticated = true,
                currentUser = user,
                error = null
            )
        } else {
            _authState.value = _authState.value.copy(
                error = "Credenciales incorrectas"
            )
        }
    }

    fun register(email: String, phoneNumber: String, password: String, confirmPassword: String) {
        // Validaciones
        if (email.isBlank() || phoneNumber.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            _authState.value = _authState.value.copy(error = "Por favor complete todos los campos")
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _authState.value = _authState.value.copy(error = "Email inválido")
            return
        }

        if (phoneNumber.length != 9 || !phoneNumber.all { it.isDigit() }) {
            _authState.value = _authState.value.copy(error = "El número debe tener 9 dígitos")
            return
        }

        if (password != confirmPassword) {
            _authState.value = _authState.value.copy(error = "Las contraseñas no coinciden")
            return
        }

        if (password.length < 4) {
            _authState.value = _authState.value.copy(error = "La contraseña debe tener al menos 4 caracteres")
            return
        }

        // Verificar si el usuario ya existe
        if (_registeredUsers.value.any { it.email == email }) {
            _authState.value = _authState.value.copy(error = "El usuario ya existe")
            return
        }

        // Crear nuevo usuario
        val newUser = User(
            email = email,
            password = password,
            phoneNumber = phoneNumber,
            isAdmin = false
        )

        _registeredUsers.value = _registeredUsers.value + newUser

        // Auto-login después del registro
        _authState.value = AuthState(
            isAuthenticated = true,
            currentUser = newUser,
            error = null
        )
    }

    fun logout() {
        _authState.value = AuthState()
    }

    fun clearError() {
        _authState.value = _authState.value.copy(error = null)
    }
}
