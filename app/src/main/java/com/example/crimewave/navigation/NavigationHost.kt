package com.example.crimewave.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crimewave.ui.viewmodel.AuthViewModel
import com.example.crimewave.ui.viewmodel.CartViewModel
import com.example.crimewave.ui.viewmodel.ClothingViewModel

/**
 * Host principal de navegación que gestiona toda la navegación de la aplicación
 */
@Composable
fun NavigationHost(
    onExitApp: () -> Unit = {}
) {
    // ViewModels
    val clothingViewModel: ClothingViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()

    // Estado de navegación
    val navigationState = remember { NavigationState() }
    
    // Estado de autenticación
    val authState by authViewModel.authState
    val isAuthenticated = authState.isAuthenticated
    val currentUser = authState.currentUser

    // Configurar el usuario actual en el CartViewModel cuando cambie el estado de autenticación
    LaunchedEffect(currentUser) {
        cartViewModel.setCurrentUser(currentUser)
    }

    // Acciones de navegación
    val navigationActions = remember(navigationState, isAuthenticated) {
        NavigationActionsImpl(
            navigationState = navigationState,
            isAuthenticated = { isAuthenticated }
        )
    }

    // BackHandler para manejar el botón de retroceso del sistema
    BackHandler {
        navigationActions.navigateBack()
    }

    // Navegador principal
    ScreenNavigator(
        navigationState = navigationState,
        navigationActions = navigationActions,
        clothingViewModel = clothingViewModel,
        authViewModel = authViewModel,
        cartViewModel = cartViewModel,
        isAuthenticated = isAuthenticated,
        currentUser = currentUser
    )

    // Diálogo de confirmación para salir de la aplicación
    ExitAppDialog(
        showDialog = navigationState.showExitDialog,
        onDismiss = { navigationActions.hideExitDialog() },
        onConfirm = onExitApp
    )
}