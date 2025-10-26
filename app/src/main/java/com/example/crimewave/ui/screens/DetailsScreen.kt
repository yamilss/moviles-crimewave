package com.example.crimewave.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.crimewave.data.model.ClothingItem
import com.example.crimewave.data.model.ProductType
import com.example.crimewave.ui.viewmodel.ClothingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    itemId: String,
    clothingViewModel: ClothingViewModel,
    onNavigateBack: () -> Unit
) {
    // Obtener el producto real del ViewModel
    val products by clothingViewModel.products
    val selectedProduct = remember(itemId, products) {
        products.find { it.id == itemId } ?: ClothingItem(
            id = itemId,
            name = "Producto no encontrado",
            description = "No se pudo encontrar el producto solicitado.",
            price = 0.0,
            imageUrl = "default_product",
            category = ProductType.POLERAS,
            isNew = false,
            sizes = listOf("N/A"),
            stock = 0,
            reviewCount = 0
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Imagen del producto
            ProductImage(imageUrl = selectedProduct.imageUrl)

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
                            text = selectedProduct.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        if (selectedProduct.isNew) {
                            NewBadge()
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "SKU: ${selectedProduct.id}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "$${String.format("%,.0f", selectedProduct.price)} CLP",
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
                        text = selectedProduct.description,
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

                    ProductInfoRow("Categoría", getCategoryText(selectedProduct.category))

                    // Mostrar tallas o medidas según el tipo de producto
                    if (selectedProduct.category == ProductType.CUADROS) {
                        ProductInfoRow("Medidas", selectedProduct.sizes.joinToString(", "))
                    } else {
                        ProductInfoRow("Tallas", selectedProduct.sizes.joinToString(", "))
                    }

                    ProductInfoRow("Stock", "${selectedProduct.stock} unidades")
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

@Composable
private fun ProductImage(imageUrl: String) {
    val imageResId = getImageResource(imageUrl)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Imagen del producto",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

private fun getImageResource(imageUrl: String): Int {
    return when (imageUrl) {
        "satorupolera" -> com.example.crimewave.R.drawable.satorupolera
        "togahoodie" -> com.example.crimewave.R.drawable.togahoodie
        "givencuadro" -> com.example.crimewave.R.drawable.givencuadro
        "polerongojo" -> com.example.crimewave.R.drawable.polerongojo
        "logocrimewave" -> com.example.crimewave.R.drawable.logocrimewave
        "bolsaanime" -> com.example.crimewave.R.drawable.bolsaanime
        "makima" -> com.example.crimewave.R.drawable.makima
        "power" -> com.example.crimewave.R.drawable.power
        else -> com.example.crimewave.R.drawable.satorupolera // imagen por defecto
    }
}

