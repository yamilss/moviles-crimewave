package com.example.crimewave.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.crimewave.ui.viewmodel.ClothingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToEditDetails: () -> Unit = {},
    onNavigateToReport: () -> Unit = {},
    onNavigateToShippingAddress: () -> Unit = {},
    onNavigateToBillingAddress: () -> Unit = {},
    onLogout: () -> Unit = {},
    userEmail: String = "",
    userPhone: String? = null,
    isAdmin: Boolean = false,
    clothingViewModel: ClothingViewModel? = null
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Configuración")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar placeholder
                    Surface(
                        modifier = Modifier.size(80.dp),
                        shape = MaterialTheme.shapes.extraLarge,
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "😢",
                                style = MaterialTheme.typography.headlineLarge
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = if (isAdmin) "Administrador" else "Cliente",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = userEmail.ifEmpty { "No especificado" },
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        if (isAdmin) {
                            // Métricas para administrador
                            val productCount = clothingViewModel?.products?.value?.size ?: 0
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$productCount",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Productos",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        } else {
                            // Métricas para cliente
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "0",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Compras",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "0",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Favoritos",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Acciones rápidas
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Acciones Rápidas",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Botones específicos para clientes
                    if (!isAdmin) {
                        // Botón para editar detalles (solo para clientes)
                        Button(
                            onClick = onNavigateToEditDetails,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Editar Mis Detalles")
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(
                            onClick = onNavigateToShippingAddress,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Direcciones de Envío")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedButton(
                            onClick = onNavigateToBillingAddress,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Direcciones de Facturación")
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Botones específicos para administrador
                    if (isAdmin) {
                        OutlinedButton(
                            onClick = onNavigateToReport,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Agregar Nuevo Producto")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedButton(
                            onClick = { /* Ver inventario */ },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Ver Inventario")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedButton(
                            onClick = onNavigateToStats,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Panel de Empleados")
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Botón de cerrar sesión
                    OutlinedButton(
                        onClick = onLogout,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Cerrar Sesión")
                    }
                }
            }
        }
    }
}
