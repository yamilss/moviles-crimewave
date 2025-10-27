package com.example.crimewave.navigation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

/**
 * Utilidades para la navegación
 */
object NavigationUtils {
    
    /**
     * Verifica si una pantalla requiere autenticación
     */
    fun requiresAuthentication(route: String): Boolean {
        return when (route) {
            Routes.LOGIN, Routes.REGISTER -> false
            else -> true
        }
    }
    
    /**
     * Verifica si una pantalla es solo para administradores
     */
    fun requiresAdmin(route: String): Boolean {
        return when (route) {
            Routes.STATS -> true
            else -> false
        }
    }
    
    /**
     * Obtiene el título de la pantalla
     */
    fun getScreenTitle(route: String): String {
        return when (route) {
            Routes.LOGIN -> "Iniciar Sesión"
            Routes.REGISTER -> "Registro"
            Routes.HOME -> "Inicio"
            Routes.PROFILE -> "Perfil"
            Routes.SETTINGS -> "Configuración"
            Routes.DETAILS -> "Detalles del Producto"
            Routes.REPORT -> "Agregar Producto"
            Routes.STATS -> "Panel de Administración"
            Routes.CART -> "Carrito de Compras"
            Routes.CHECKOUT -> "Finalizar Compra"
            Routes.ORDER_RESULT -> "Resultado del Pedido"
            Routes.EDIT_DETAILS -> "Editar Detalles"
            Routes.SHIPPING_ADDRESS -> "Dirección de Envío"
            Routes.BILLING_ADDRESS -> "Dirección de Facturación"
            Routes.VIEW_SHIPPING_ADDRESS -> "Ver Dirección de Envío"
            Routes.VIEW_BILLING_ADDRESS -> "Ver Dirección de Facturación"
            else -> "Crimewave"
        }
    }
}

/**
 * Diálogo de confirmación para salir de la aplicación
 */
@Composable
fun ExitAppDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text("Salir de la aplicación")
            },
            text = {
                Text("¿Estás seguro de que quieres cerrar la aplicación?")
            },
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text("Salir")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        )
    }
}