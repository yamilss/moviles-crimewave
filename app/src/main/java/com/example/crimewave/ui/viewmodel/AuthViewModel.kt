package com.example.crimewave.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.example.crimewave.data.model.User
import com.example.crimewave.data.model.AuthState
import com.example.crimewave.data.model.ShippingAddress
import com.example.crimewave.data.model.BillingAddress
import com.example.crimewave.data.repository.UserRepository

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(application.applicationContext)

    private val _authState = mutableStateOf(AuthState())
    val authState: State<AuthState> = _authState

    init {
        // Asegurar que existan usuarios por defecto
        userRepository.forceReinitializeUsers()

        // Siempre empezar en estado no autenticado para mostrar login
        _authState.value = AuthState(
            isAuthenticated = false,
            currentUser = null,
            error = null
        )
    }

    fun login(email: String, password: String) {
        // Limpiar error previo
        _authState.value = _authState.value.copy(error = null)

        if (email.isBlank() || password.isBlank()) {
            _authState.value = _authState.value.copy(error = "Por favor complete todos los campos")
            return
        }

        // Buscar usuario
        val user = userRepository.authenticateUser(email.trim(), password.trim())

        if (user != null) {
            userRepository.saveCurrentUser(user)
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

        // Crear nuevo usuario
        val newUser = User(
            email = email,
            password = password,
            phoneNumber = phoneNumber,
            isAdmin = false
        )

        // Intentar registrar el usuario
        val success = userRepository.registerUser(newUser)

        if (success) {
            // Auto-login después del registro
            userRepository.saveCurrentUser(newUser)
            _authState.value = AuthState(
                isAuthenticated = true,
                currentUser = newUser,
                error = null
            )
        } else {
            _authState.value = _authState.value.copy(error = "El usuario ya existe")
        }
    }

    fun logout() {
        userRepository.logout()
        _authState.value = AuthState(
            isAuthenticated = false,
            currentUser = null,
            error = null
        )
    }

    fun clearError() {
        _authState.value = _authState.value.copy(error = null)
    }

    fun updateUserDetails(
        currentPassword: String,
        newPhone: String?,
        newPassword: String?
    ): Boolean {
        val currentUser = _authState.value.currentUser ?: return false

        // Verificar contraseña actual
        if (currentPassword.isBlank() || currentUser.password != currentPassword) {
            _authState.value = _authState.value.copy(error = "Contraseña actual incorrecta")
            return false
        }

        // Validar nuevo teléfono si se proporciona
        if (!newPhone.isNullOrBlank()) {
            if (newPhone.length != 9 || !newPhone.all { it.isDigit() }) {
                _authState.value = _authState.value.copy(error = "El número debe tener 9 dígitos")
                return false
            }
        }

        // Validar nueva contraseña si se proporciona
        if (!newPassword.isNullOrBlank()) {
            if (newPassword.length < 4) {
                _authState.value = _authState.value.copy(error = "La contraseña debe tener al menos 4 caracteres")
                return false
            }
        }

        // Crear usuario actualizado
        val updatedUser = currentUser.copy(
            phoneNumber = newPhone?.takeIf { it.isNotBlank() } ?: currentUser.phoneNumber,
            password = newPassword?.takeIf { it.isNotBlank() } ?: currentUser.password
        )

        // Actualizar en el repositorio
        userRepository.updateUser(updatedUser)

        // Actualizar el usuario actual en la sesión
        _authState.value = _authState.value.copy(
            currentUser = updatedUser,
            error = null
        )

        return true
    }

    fun saveShippingAddress(
        nombre: String,
        apellidos: String,
        direccion: String,
        rut: String,
        ciudad: String,
        codigoPostal: String,
        pais: String,
        region: String,
        celular: String,
        instagram: String
    ): Boolean {
        val currentUser = _authState.value.currentUser ?: return false

        // Validaciones
        if (nombre.isBlank() || apellidos.isBlank() || direccion.isBlank() ||
            rut.isBlank() || ciudad.isBlank() || codigoPostal.isBlank() ||
            pais.isBlank() || region.isBlank() || celular.isBlank()) {
            _authState.value = _authState.value.copy(error = "Por favor complete todos los campos obligatorios")
            return false
        }

        // Validar celular de 9 dígitos
        if (celular.length != 9 || !celular.all { it.isDigit() }) {
            _authState.value = _authState.value.copy(error = "El celular debe tener exactamente 9 dígitos")
            return false
        }

        // Validar RUT (exactamente 9 dígitos numéricos, igual que el celular)
        if (rut.length != 9 || !rut.all { it.isDigit() }) {
            _authState.value = _authState.value.copy(error = "El RUT debe tener exactamente 9 dígitos")
            return false
        }

        val shippingAddress = com.example.crimewave.data.model.ShippingAddress(
            nombre = nombre,
            apellidos = apellidos,
            direccion = direccion,
            rut = rut,
            ciudad = ciudad,
            codigoPostal = codigoPostal,
            pais = pais,
            region = region,
            celular = celular,
            instagram = instagram
        )

        val updatedUser = currentUser.copy(shippingAddress = shippingAddress)

        // Actualizar en el repositorio
        userRepository.updateUser(updatedUser)

        // Actualizar el usuario actual en la sesión
        _authState.value = _authState.value.copy(currentUser = updatedUser)

        return true
    }

    fun saveBillingAddress(
        nombre: String,
        apellidos: String,
        direccion: String,
        rut: String,
        ciudad: String,
        codigoPostal: String,
        pais: String,
        region: String,
        celular: String,
        instagram: String
    ): Boolean {
        val currentUser = _authState.value.currentUser ?: return false

        // Validaciones
        if (nombre.isBlank() || apellidos.isBlank() || direccion.isBlank() ||
            rut.isBlank() || ciudad.isBlank() || codigoPostal.isBlank() ||
            pais.isBlank() || region.isBlank() || celular.isBlank()) {
            _authState.value = _authState.value.copy(error = "Por favor complete todos los campos obligatorios")
            return false
        }

        // Validar celular de 9 dígitos
        if (celular.length != 9 || !celular.all { it.isDigit() }) {
            _authState.value = _authState.value.copy(error = "El celular debe tener exactamente 9 dígitos")
            return false
        }

        // Validar RUT (exactamente 9 dígitos numéricos, igual que el celular)
        if (rut.length != 9 || !rut.all { it.isDigit() }) {
            _authState.value = _authState.value.copy(error = "El RUT debe tener exactamente 9 dígitos")
            return false
        }

        val billingAddress = com.example.crimewave.data.model.BillingAddress(
            nombre = nombre,
            apellidos = apellidos,
            direccion = direccion,
            rut = rut,
            ciudad = ciudad,
            codigoPostal = codigoPostal,
            pais = pais,
            region = region,
            celular = celular,
            instagram = instagram
        )

        val updatedUser = currentUser.copy(billingAddress = billingAddress)

        // Actualizar en el repositorio
        userRepository.updateUser(updatedUser)

        // Actualizar el usuario actual en la sesión
        _authState.value = _authState.value.copy(currentUser = updatedUser)

        return true
    }

    fun deleteShippingAddress(): Boolean {
        val currentUser = _authState.value.currentUser ?: return false
        val updatedUser = currentUser.copy(shippingAddress = null)

        // Actualizar en el repositorio
        userRepository.updateUser(updatedUser)

        // Actualizar el usuario actual en la sesión
        _authState.value = _authState.value.copy(currentUser = updatedUser)
        return true
    }



    fun deleteBillingAddress(): Boolean {
        val currentUser = _authState.value.currentUser ?: return false
        val updatedUser = currentUser.copy(billingAddress = null)

        // Actualizar en el repositorio
        userRepository.updateUser(updatedUser)

        // Actualizar el usuario actual en la sesión
        _authState.value = _authState.value.copy(currentUser = updatedUser)
        return true
    }

    // Función para reinicializar usuarios predeterminados (útil para debugging)
    fun reinitializeUsers() {
        userRepository.forceReinitializeUsers()
    }

    // Función para obtener la lista de usuarios registrados (para debugging)
    fun getRegisteredUsers(): List<User> {
        return userRepository.getRegisteredUsers()
    }

    // Función para verificar si un usuario existe
    fun userExists(email: String): Boolean {
        return userRepository.getRegisteredUsers().any { it.email == email }
    }
}
