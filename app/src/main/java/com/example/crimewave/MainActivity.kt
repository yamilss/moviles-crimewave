package com.example.crimewave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
                CrimewaveApp(
                    onExitApp = { finish() }
                )
            }
        }
    }
}

@Composable
fun CrimewaveApp(
    onExitApp: () -> Unit = {}
) {
    val clothingViewModel: ClothingViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()

    var currentScreen by remember { mutableStateOf("login") }
    var selectedItemId by remember { mutableStateOf("") }
    var navigationStack by remember { mutableStateOf(listOf<String>()) }
    var showExitDialog by remember { mutableStateOf(false) }

    val authState by authViewModel.authState

    // Validaciones de seguridad para evitar NPEs y navegación incorrecta
    val isAuthenticated = authState.isAuthenticated
    val currentUser = authState.currentUser

    // Si está autenticado, asegurar que no esté en pantallas de auth
    if (isAuthenticated && (currentScreen == "login" || currentScreen == "register")) {
        currentScreen = "home"
        navigationStack = emptyList()
    }

    // Función para navegar agregando al historial
    fun navigateToScreen(screen: String) {
        if (currentScreen != screen) {
            navigationStack = navigationStack + currentScreen
            currentScreen = screen
        }
    }

    // Función para navegar hacia atrás
    fun navigateBack() {
        if (navigationStack.isNotEmpty()) {
            currentScreen = navigationStack.lastOrNull() ?: "home"
            navigationStack = navigationStack.dropLast(1)
        } else if (currentScreen == "home") {
            showExitDialog = true
        } else if (currentScreen == "login" || currentScreen == "register") {
            // Mostrar diálogo de confirmación también en pantallas de autenticación
            showExitDialog = true
        } else if (isAuthenticated) {
            currentScreen = "home"
        } else {
            currentScreen = "login"
        }
    }

    // BackHandler para manejar el botón de retroceso del sistema
    BackHandler {
        navigateBack()
    }

    // Navegación condicional basada en autenticación
    if (isAuthenticated) {
        // Usuario autenticado - mostrar aplicación principal
        when (currentScreen) {
            "login", "register" -> currentScreen = "home" // Redirigir a home si ya está autenticado
            "home" -> HomeScreen(
                clothingViewModel = clothingViewModel,
                onNavigateToProfile = { navigateToScreen("profile") },
                onNavigateToDetails = { itemId ->
                    // Validar que el itemId no sea nulo o vacío antes de navegar
                    if (!itemId.isNullOrBlank()) {
                        selectedItemId = itemId
                        navigateToScreen("details")
                    }
                },
                onNavigateToReport = { navigateToScreen("report") },
                isAdmin = currentUser?.isAdmin ?: false
            )
            "profile" -> ProfileScreen(
                onNavigateBack = { navigateBack() },
                onNavigateToSettings = { navigateToScreen("settings") },
                onNavigateToStats = {
                    // Solo permitir acceso a stats si es administrador
                    if (currentUser?.isAdmin == true) {
                        navigateToScreen("stats")
                    }
                },
                onNavigateToEditDetails = { navigateToScreen("editDetails") },
                onNavigateToReport = { navigateToScreen("report") },
                onNavigateToShippingAddress = { navigateToScreen("viewShippingAddress") },
                onNavigateToBillingAddress = { navigateToScreen("viewBillingAddress") },
                onLogout = {
                    authViewModel.logout()
                    navigationStack = emptyList()
                    currentScreen = "login"
                },
                userEmail = currentUser?.email ?: "",
                userPhone = currentUser?.phoneNumber,
                isAdmin = currentUser?.isAdmin ?: false
            )
            "settings" -> SettingsScreen(
                onNavigateBack = { navigateBack() }
            )
            "details" -> {
                // Validar que exista un id seleccionado válido
                if (selectedItemId.isNotBlank()) {
                    DetailsScreen(
                        itemId = selectedItemId,
                        clothingViewModel = clothingViewModel,
                        onNavigateBack = { navigateBack() }
                    )
                } else {
                    // Si no hay id válido, regresar a home
                    currentScreen = "home"
                }
            }
            "report" -> ReportScreen(
                clothingViewModel = clothingViewModel,
                onNavigateBack = { navigateBack() },
                onReportSubmitted = {
                    navigationStack = emptyList()
                    currentScreen = "home"
                }
            )
            "stats" -> {
                // Pantalla de administración: solo accesible si es admin
                if (currentUser?.isAdmin == true) {
                    EmployeePanelScreen(
                        clothingViewModel = clothingViewModel,
                        onNavigateBack = { navigateBack() },
                        onNavigateToAddProduct = { navigateToScreen("report") }
                    )
                } else {
                    currentScreen = "home"
                }
            }
            "editDetails" -> EditDetailsScreen(
                authViewModel = authViewModel,
                onNavigateBack = { navigateBack() },
                onUpdateSuccess = { navigateBack() },
                currentEmail = currentUser?.email ?: "",
                currentPhone = currentUser?.phoneNumber
            )
            "viewShippingAddress" -> ViewShippingAddressScreen(
                onNavigateBack = { navigateBack() },
                onNavigateToAdd = { navigateToScreen("shippingAddress") },
                authViewModel = authViewModel
            )
            "viewBillingAddress" -> ViewBillingAddressScreen(
                onNavigateBack = { navigateBack() },
                onNavigateToAdd = { navigateToScreen("billingAddress") },
                authViewModel = authViewModel
            )
            "shippingAddress" -> ShippingAddressScreen(
                onNavigateBack = { navigateBack() },
                authViewModel = authViewModel
            )
            "billingAddress" -> BillingAddressScreen(
                onNavigateBack = { navigateBack() },
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
                onNavigateToRegister = { navigateToScreen("register") },
                onBackPressed = { navigateBack() }
            )
            "register" -> RegisterScreen(
                authViewModel = authViewModel,
                onRegisterSuccess = { currentScreen = "home" },
                onNavigateToLogin = { navigateBack() },
                onBackPressed = { navigateBack() }
            )
            else -> {
                // Si por alguna razón está en otra pantalla sin estar autenticado, redirigir a login
                currentScreen = "login"
            }
        }
    }

    // Diálogo de confirmación para salir de la aplicación
    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = {
                Text("Salir de la aplicación")
            },
            text = {
                Text("¿Estás seguro de que quieres cerrar la aplicación?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Cerrar la aplicación
                        onExitApp()
                    }
                ) {
                    Text("Salir")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showExitDialog = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
