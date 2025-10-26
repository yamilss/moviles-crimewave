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

    // Navegación condicional basada en autenticación
    if (authState.isAuthenticated) {
        // Usuario autenticado - mostrar aplicación principal
        when (currentScreen) {
            "login", "register" -> currentScreen = "home" // Redirigir a home si ya está autenticado
            "home" -> HomeScreen(
                clothingViewModel = clothingViewModel,
                onNavigateToProfile = { currentScreen = "profile" },
                onNavigateToDetails = { itemId ->
                    selectedItemId = itemId
                    currentScreen = "details"
                },
                onNavigateToReport = { currentScreen = "report" }
            )
            "profile" -> ProfileScreen(
                onNavigateBack = { currentScreen = "home" },
                onNavigateToSettings = { currentScreen = "settings" },
                onNavigateToStats = { currentScreen = "stats" },
                onNavigateToEditDetails = { currentScreen = "editDetails" },
                onNavigateToReport = { currentScreen = "report" },
                onNavigateToShippingAddress = { currentScreen = "shippingAddress" },
                onNavigateToBillingAddress = { currentScreen = "billingAddress" },
                onLogout = {
                    authViewModel.logout()
                    currentScreen = "login"
                },
                userEmail = authState.currentUser?.email ?: "",
                userPhone = authState.currentUser?.phoneNumber,
                isAdmin = authState.currentUser?.isAdmin ?: false
            )
            "settings" -> SettingsScreen(
                onNavigateBack = { currentScreen = "profile" }
            )
            "details" -> DetailsScreen(
                itemId = selectedItemId,
                onNavigateBack = { currentScreen = "home" }
            )
            "report" -> ReportScreen(
                clothingViewModel = clothingViewModel,
                onNavigateBack = { currentScreen = "home" },
                onReportSubmitted = {
                    currentScreen = "home"
                }
            )
            "stats" -> EmployeePanelScreen(
                clothingViewModel = clothingViewModel,
                onNavigateBack = { currentScreen = "profile" },
                onNavigateToAddProduct = { currentScreen = "report" }
            )
            "editDetails" -> EditDetailsScreen(
                authViewModel = authViewModel,
                onNavigateBack = { currentScreen = "profile" },
                onUpdateSuccess = { currentScreen = "profile" },
                currentEmail = authState.currentUser?.email ?: "",
                currentPhone = authState.currentUser?.phoneNumber
            )
            "shippingAddress" -> ShippingAddressScreen(
                onNavigateBack = { currentScreen = "profile" }
            )
            "billingAddress" -> BillingAddressScreen(
                onNavigateBack = { currentScreen = "profile" }
            )
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
