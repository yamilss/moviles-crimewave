package com.example.crimewave.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Administrador del estado de navegación de la aplicación
 */
class NavigationState {
    var currentScreen by mutableStateOf(Routes.LOGIN)
        private set
    
    var previousScreen by mutableStateOf(Routes.LOGIN)
        private set
    
    private var _selectedItemId by mutableStateOf("")
    val selectedItemId: String get() = _selectedItemId
    
    var navigationStack by mutableStateOf(listOf<String>())
        private set
    
    var showExitDialog by mutableStateOf(false)
        private set

    /**
     * Navega a una nueva pantalla agregando la actual al historial
     */
    fun navigateToScreen(screen: String) {
        if (currentScreen != screen) {
            navigationStack = navigationStack + currentScreen
            previousScreen = currentScreen
            currentScreen = screen
        }
    }

    /**
     * Navega hacia atrás usando el historial
     */
    fun navigateBack(isAuthenticated: Boolean, onShowExitDialog: () -> Unit) {
        when {
            navigationStack.isNotEmpty() -> {
                previousScreen = currentScreen
                currentScreen = navigationStack.lastOrNull() ?: Routes.HOME
                navigationStack = navigationStack.dropLast(1)
            }
            currentScreen == Routes.HOME -> {
                onShowExitDialog()
            }
            currentScreen == Routes.LOGIN || currentScreen == Routes.REGISTER -> {
                onShowExitDialog()
            }
            isAuthenticated -> {
                previousScreen = currentScreen
                currentScreen = Routes.HOME
            }
            else -> {
                previousScreen = currentScreen
                currentScreen = Routes.LOGIN
            }
        }
    }

    /**
     * Establece el ID del item seleccionado (para navegación a detalles)
     */
    fun setSelectedItemId(itemId: String) {
        _selectedItemId = itemId
    }

    /**
     * Muestra el diálogo de salida
     */
    fun showExitDialog() {
        showExitDialog = true
    }

    /**
     * Oculta el diálogo de salida
     */
    fun hideExitDialog() {
        showExitDialog = false
    }

    /**
     * Navega directamente a una pantalla sin agregar al historial
     */
    fun navigateDirectly(screen: String) {
        previousScreen = currentScreen
        currentScreen = screen
    }

    /**
     * Limpia el historial de navegación
     */
    fun clearNavigationStack() {
        navigationStack = emptyList()
    }

    /**
     * Restablece el estado de navegación (útil para logout)
     */
    fun reset() {
        navigationStack = emptyList()
        previousScreen = currentScreen
        currentScreen = Routes.LOGIN
        _selectedItemId = ""
        showExitDialog = false
    }
}