package com.example.crimewave.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.crimewave.data.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserRepository(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val KEY_USERS = "registered_users"
        private const val KEY_CURRENT_USER = "current_user"
        private const val KEY_IS_AUTHENTICATED = "is_authenticated"
    }

    init {
        // Inicializar usuarios por defecto si no existen
        if (getRegisteredUsers().isEmpty()) {
            initializeDefaultUsers()
        }
    }

    private fun initializeDefaultUsers() {
        val defaultUsers = listOf(
            User(
                email = "admin",
                password = "admin",
                phoneNumber = "123456789",
                isAdmin = true
            ),
            User(
                email = "hola@gmail.com",
                password = "hola",
                phoneNumber = "987654321",
                isAdmin = false
            )
        )

        // Limpiar usuarios existentes y guardar los nuevos
        sharedPreferences.edit().remove(KEY_USERS).commit()
        saveRegisteredUsers(defaultUsers)
    }

    // Función para forzar la reinicialización de usuarios (útil para debugging)
    fun forceReinitializeUsers() {
        // Limpiar completamente el estado de autenticación
        sharedPreferences.edit()
            .remove(KEY_USERS)
            .remove(KEY_CURRENT_USER)
            .putBoolean(KEY_IS_AUTHENTICATED, false)
            .commit()

        // Reinicializar usuarios por defecto
        initializeDefaultUsers()
    }

    fun getRegisteredUsers(): List<User> {
        val usersJson = sharedPreferences.getString(KEY_USERS, "[]")
        val type = object : TypeToken<List<User>>() {}.type
        return gson.fromJson(usersJson, type) ?: emptyList()
    }

    private fun saveRegisteredUsers(users: List<User>) {
        val usersJson = gson.toJson(users)
        sharedPreferences.edit()
            .putString(KEY_USERS, usersJson)
            .commit() // Usar commit() en lugar de apply() para sincronización inmediata
    }

    fun registerUser(user: User): Boolean {
        val currentUsers = getRegisteredUsers().toMutableList()

        // Verificar si el usuario ya existe
        if (currentUsers.any { it.email == user.email }) {
            return false
        }

        currentUsers.add(user)
        saveRegisteredUsers(currentUsers)

        // Verificar que se guardó correctamente
        val savedUsers = getRegisteredUsers()
        return savedUsers.any { it.email == user.email }
    }

    fun authenticateUser(email: String, password: String): User? {
        val users = getRegisteredUsers()
        return users.find { it.email == email && it.password == password }
    }

    fun saveCurrentUser(user: User) {
        val userJson = gson.toJson(user)
        sharedPreferences.edit()
            .putString(KEY_CURRENT_USER, userJson)
            .putBoolean(KEY_IS_AUTHENTICATED, true)
            .commit() // Usar commit() para sincronización inmediata
    }

    fun getCurrentUser(): User? {
        val userJson = sharedPreferences.getString(KEY_CURRENT_USER, null)
        return if (userJson != null) {
            gson.fromJson(userJson, User::class.java)
        } else null
    }

    fun isAuthenticated(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_AUTHENTICATED, false)
    }

    fun logout() {
        sharedPreferences.edit()
            .remove(KEY_CURRENT_USER)
            .putBoolean(KEY_IS_AUTHENTICATED, false)
            .commit() // Usar commit() para sincronización inmediata
    }

    fun updateUser(updatedUser: User) {
        val currentUsers = getRegisteredUsers().toMutableList()
        val userIndex = currentUsers.indexOfFirst { it.email == updatedUser.email }

        if (userIndex >= 0) {
            currentUsers[userIndex] = updatedUser
            saveRegisteredUsers(currentUsers)

            // Si es el usuario actual, actualizar también la sesión
            if (getCurrentUser()?.email == updatedUser.email) {
                saveCurrentUser(updatedUser)
            }
        }
    }

    // Función para verificar el estado de los datos (debugging)
    fun getDataStatus(): String {
        val users = getRegisteredUsers()
        val currentUser = getCurrentUser()
        val isAuth = isAuthenticated()

        return "Usuarios: ${users.size}, Usuario actual: ${currentUser?.email ?: "none"}, Autenticado: $isAuth"
    }
}
