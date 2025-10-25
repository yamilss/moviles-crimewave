package com.example.crimewave.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.crimewave.data.model.ClothingItem
import com.example.crimewave.data.model.ProductType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    itemId: String,
    onNavigateBack: () -> Unit
) {
    // Producto de ejemplo basado en el ID
    val sampleProduct = remember {
        ClothingItem(
            id = itemId,
            name = "Polera Satoru Gojo",
            description = "Diseño exclusivo de araña con estilo anime. Fabricada en algodón 100% premium con diseño de alta calidad que no se desvanece.",
            price = 22.000,
            imageUrl = "producto_gojo",
            category = ProductType.POLERAS,
            isNew = true,
            sizes = listOf("S", "M", "L", "XL"),
            colors = listOf("Negro", "Blanco", "Gris"),
            stock = 15,
            rating = 4.8f,
            reviewCount = 24
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles del Producto") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Información principal del producto
            Card {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = sampleProduct.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        if (sampleProduct.isNew) {
                            NewBadge()
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "SKU: ${sampleProduct.id}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "$${sampleProduct.price}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Descripción del producto
            Card {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Descripción",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = sampleProduct.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Información del producto
            Card {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Información del Producto",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    ProductInfoRow("Categoría", getCategoryText(sampleProduct.category))
                    ProductInfoRow("Tallas", sampleProduct.sizes.joinToString(", "))
                    ProductInfoRow("Colores", sampleProduct.colors.joinToString(", "))
                    ProductInfoRow("Stock", "${sampleProduct.stock} unidades")
                    ProductInfoRow("Rating", "⭐ ${sampleProduct.rating} (${sampleProduct.reviewCount} reseñas)")
                }
            }

            // Acciones de compra
            Card {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Acciones",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Button(
                        onClick = { /* Agregar al carrito */ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Agregar al Carrito")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = { /* Agregar a favoritos */ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Agregar a Favoritos")
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun NewBadge() {
    Surface(
        color = Color(0xFF4CAF50).copy(alpha = 0.1f),
        contentColor = Color(0xFF4CAF50),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = "NUEVO",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

private fun getCategoryText(category: ProductType): String {
    return when (category) {
        ProductType.POLERAS -> "👕 Poleras"
        ProductType.POLERONES -> "🧥 Polerones"
        ProductType.CUADROS -> "🖼️ Cuadros"
    }
}
