package com.example.crimewave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crimewave.ui.screens.HomeScreen
import com.example.crimewave.ui.screens.ProfileScreen
import com.example.crimewave.ui.screens.SettingsScreen
import com.example.crimewave.ui.screens.DetailsScreen
import com.example.crimewave.ui.screens.ReportScreen
import com.example.crimewave.ui.screens.EmployeePanelScreen
import com.example.crimewave.ui.screens.LoginScreen
import com.example.crimewave.ui.screens.RegisterScreen
import com.example.crimewave.ui.screens.EditDetailsScreen
import com.example.crimewave.ui.screens.ShippingAddressScreen
import com.example.crimewave.ui.screens.BillingAddressScreen
import com.example.crimewave.ui.screens.ViewShippingAddressScreen
import com.example.crimewave.ui.screens.ViewBillingAddressScreen
import com.example.crimewave.ui.viewmodel.ClothingViewModel
import com.example.crimewave.ui.viewmodel.AuthViewModel
import com.example.crimewave.ui.theme.CrimewaveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CrimewaveTheme {
                CrimewaveApp()
            }
        }
    }
}

@Composable
fun CrimewaveApp() {
    val clothingViewModel: ClothingViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()

    var currentScreen by remember { mutableStateOf("login") }
    var selectedItemId by remember { mutableStateOf("") }

    val authState by authViewModel.authState

    // Validaciones de seguridad para evitar NPEs y navegación incorrecta
    val isAuthenticated = authState.isAuthenticated
    val currentUser = authState.currentUser

    // Si está autenticado, asegurar que no esté en pantallas de auth
    if (isAuthenticated && (currentScreen == "login" || currentScreen == "register")) {
        currentScreen = "home"
    }

    // Navegación condicional basada en autenticación
    if (isAuthenticated) {
        // Usuario autenticado - mostrar aplicación principal
        when (currentScreen) {
            "login", "register" -> currentScreen = "home" // Redirigir a home si ya está autenticado
            "home" -> HomeScreen(
                clothingViewModel = clothingViewModel,
                onNavigateToProfile = { currentScreen = "profile" },
                onNavigateToDetails = { itemId ->
                    // Validar que el itemId no sea nulo o vacío antes de navegar
                    if (!itemId.isNullOrBlank()) {
                        selectedItemId = itemId
                        currentScreen = "details"
                    }
                },
                onNavigateToReport = { currentScreen = "report" },
                isAdmin = currentUser?.isAdmin ?: false
            )
            "profile" -> ProfileScreen(
                onNavigateBack = { currentScreen = "home" },
                onNavigateToSettings = { currentScreen = "settings" },
                onNavigateToStats = {
                    // Solo permitir acceso a stats si es administrador
                    if (currentUser?.isAdmin == true) {
                        currentScreen = "stats"
                    }
                },
                onNavigateToEditDetails = { currentScreen = "editDetails" },
                onNavigateToReport = { currentScreen = "report" },
                onNavigateToShippingAddress = { currentScreen = "viewShippingAddress" },
                onNavigateToBillingAddress = { currentScreen = "viewBillingAddress" },
                onLogout = {
                    authViewModel.logout()
                    currentScreen = "login"
                },
                userEmail = currentUser?.email ?: "",
                userPhone = currentUser?.phoneNumber,
                isAdmin = currentUser?.isAdmin ?: false
            )
            "settings" -> SettingsScreen(
                onNavigateBack = { currentScreen = "profile" }
            )
            "details" -> {
                // Validar que exista un id seleccionado válido
                if (selectedItemId.isNotBlank()) {
                    DetailsScreen(
                        itemId = selectedItemId,
                        clothingViewModel = clothingViewModel,
                        onNavigateBack = { currentScreen = "home" }
                    )
                } else {
                    // Si no hay id válido, regresar a home
                    currentScreen = "home"
                }
            }
            "report" -> ReportScreen(
                clothingViewModel = clothingViewModel,
                onNavigateBack = { currentScreen = "home" },
                onReportSubmitted = {
                    currentScreen = "home"
                }
            )
            "stats" -> {
                // Pantalla de administración: solo accesible si es admin
                if (currentUser?.isAdmin == true) {
                    EmployeePanelScreen(
                        clothingViewModel = clothingViewModel,
                        onNavigateBack = { currentScreen = "profile" },
                        onNavigateToAddProduct = { currentScreen = "report" }
                    )
                } else {
                    currentScreen = "home"
                }
            }
            "editDetails" -> EditDetailsScreen(
                authViewModel = authViewModel,
                onNavigateBack = { currentScreen = "profile" },
                onUpdateSuccess = { currentScreen = "profile" },
                currentEmail = currentUser?.email ?: "",
                currentPhone = currentUser?.phoneNumber
            )
            "viewShippingAddress" -> ViewShippingAddressScreen(
                onNavigateBack = { currentScreen = "profile" },
                onNavigateToAdd = { currentScreen = "shippingAddress" },
                authViewModel = authViewModel
            )
            "viewBillingAddress" -> ViewBillingAddressScreen(
                onNavigateBack = { currentScreen = "profile" },
                onNavigateToAdd = { currentScreen = "billingAddress" },
                authViewModel = authViewModel
            )
            "shippingAddress" -> ShippingAddressScreen(
                onNavigateBack = { currentScreen = "viewShippingAddress" },
                authViewModel = authViewModel
            )
            "billingAddress" -> BillingAddressScreen(
                onNavigateBack = { currentScreen = "viewBillingAddress" },
                authViewModel = authViewModel
            )
            else -> {
                // Pantalla desconocida - redirigir a home por seguridad
                currentScreen = "home"
            }
        }
    } else {
        // Usuario no autenticado - mostrar pantallas de autenticación
        when (currentScreen) {
            "login" -> LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = { currentScreen = "home" },
                onNavigateToRegister = { currentScreen = "register" }
            )
            "register" -> RegisterScreen(
                authViewModel = authViewModel,
                onRegisterSuccess = { currentScreen = "home" },
                onNavigateToLogin = { currentScreen = "login" }
            )
            else -> {
                // Si por alguna razón está en otra pantalla sin estar autenticado, redirigir a login
                currentScreen = "login"
            }
        }
    }
}
