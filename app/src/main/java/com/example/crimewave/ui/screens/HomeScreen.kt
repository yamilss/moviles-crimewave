package com.example.crimewave.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.crimewave.ui.components.ProductCard
import com.example.crimewave.ui.viewmodel.ClothingViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    clothingViewModel: ClothingViewModel,
    cartViewModel: com.example.crimewave.ui.viewmodel.CartViewModel,
    onNavigateToProfile: () -> Unit,
    onNavigateToDetails: (String) -> Unit,
    onNavigateToReport: () -> Unit,
    onNavigateToCart: () -> Unit,
    isAdmin: Boolean = false
) {
    // Obtener productos del ViewModel
    val allProducts by clothingViewModel.products
    val featuredProducts = allProducts.take(6) // Mostrar los primeros 6 productos
    val cartState by cartViewModel.cartState

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálago") },
                actions = {
                    // Botón del carrito con badge
                    Box {
                        IconButton(onClick = onNavigateToCart) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                        }
                        if (cartState.totalItems > 0) {
                            Badge(
                                modifier = Modifier.offset(x = (-8).dp, y = 8.dp)
                            ) {
                                Text(
                                    text = cartState.totalItems.toString(),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }

                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil")
                    }
                }
            )
        },
        floatingActionButton = {
            if (isAdmin) {
                FloatingActionButton(
                    onClick = onNavigateToReport
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar Producto")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Productos Destacados",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(featuredProducts) { product ->
                    ProductCard(
                        product = product,
                        onClick = { onNavigateToDetails(product.id) }
                    )
                }
            }
        }
    }
}
