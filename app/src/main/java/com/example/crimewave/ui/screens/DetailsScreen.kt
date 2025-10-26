package com.example.crimewave.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.crimewave.data.model.ClothingItem
import com.example.crimewave.data.model.ProductType
import com.example.crimewave.ui.viewmodel.ClothingViewModel
import com.example.crimewave.ui.viewmodel.CartViewModel
import com.example.crimewave.ui.components.ProductImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    itemId: String,
    clothingViewModel: ClothingViewModel,
    cartViewModel: CartViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToCart: () -> Unit
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

    var selectedSize by remember { mutableStateOf(selectedProduct.sizes.firstOrNull() ?: "") }
    var showAddedToCartMessage by remember { mutableStateOf(false) }

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
            ProductImage(
                imageUrl = selectedProduct.imageUrl,
                contentDescription = selectedProduct.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

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
                        text = "Envíos gratis en todo 🇨🇱 en compras sobre 50k ",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "$${String.format(java.util.Locale.forLanguageTag("es-CL"), "%,.0f", selectedProduct.price)} CLP",
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

                    // Sección de tallas/medidas seleccionables
                    if (selectedProduct.sizes.isNotEmpty() && selectedProduct.sizes != listOf("N/A")) {
                        Column {
                            Text(
                                text = if (selectedProduct.category == ProductType.CUADROS) "Medidas Disponibles" else "Tallas Disponibles",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                selectedProduct.sizes.take(4).forEach { size ->
                                    FilterChip(
                                        onClick = { selectedSize = size },
                                        label = { Text(size) },
                                        selected = selectedSize == size,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }

                            // Segunda fila si hay más de 4 tallas
                            if (selectedProduct.sizes.size > 4) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    selectedProduct.sizes.drop(4).forEach { size ->
                                        FilterChip(
                                            onClick = { selectedSize = size },
                                            label = { Text(size) },
                                            selected = selectedSize == size,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                    // Espacios vacíos si no completan la fila
                                    repeat(4 - selectedProduct.sizes.drop(4).size) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
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
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    if (selectedProduct.stock > 0) {
                        Button(
                            onClick = {
                                if (selectedSize.isNotEmpty()) {
                                    cartViewModel.addToCart(selectedProduct, selectedSize)
                                    showAddedToCartMessage = true
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = selectedSize.isNotEmpty()
                        ) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Agregar al Carrito")
                        }

                        if (selectedSize.isEmpty() && selectedProduct.sizes.isNotEmpty() && selectedProduct.sizes != listOf("N/A")) {
                            Text(
                                text = if (selectedProduct.category == ProductType.CUADROS) "* Selecciona una medida" else "* Selecciona una talla",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    } else {
                        Button(
                            onClick = { },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = false
                        ) {
                            Text("Sin Stock")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = onNavigateToCart,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Ver el Carrito")
                    }
                }
            }
        }

        // Mensaje de confirmación
        if (showAddedToCartMessage) {
            LaunchedEffect(showAddedToCartMessage) {
                kotlinx.coroutines.delay(2000)
                showAddedToCartMessage = false
            }
        }
    }

    // Snackbar de confirmación
    if (showAddedToCartMessage) {
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(2000)
            showAddedToCartMessage = false
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



