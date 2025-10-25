package com.example.crimewave.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.crimewave.data.model.ClothingItem
import com.example.crimewave.data.model.ProductType
import com.example.crimewave.ui.components.ProductCard
import com.example.crimewave.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToDetails: (String) -> Unit,
    onNavigateToReport: () -> Unit
) {
    // Productos destacados de ejemplo
    val featuredProducts = remember {
        listOf(
            ClothingItem(
                id = "1",
                name = "Polera Araña",
                description = "Diseño exclusivo de araña con estilo anime",
                price = 25.99,
                imageUrl = "producto_arana",
                category = ProductType.POLERAS,
                isNew = true
            ),
            ClothingItem(
                id = "2",
                name = "Polerón Anime",
                description = "Polerón con diseños anime exclusivos",
                price = 42.00,
                imageUrl = "producto_poleron",
                category = ProductType.POLERONES,
                isFeatured = true
            ),
            ClothingItem(
                id = "3",
                name = "Cuadro Anime",
                description = "Cuadros decorativos con diseños de anime",
                price = 45.00,
                imageUrl = "producto_cuadros",
                category = ProductType.CUADROS,
                isNew = true
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tienda Anime") },
                actions = {
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToReport
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Producto")
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
