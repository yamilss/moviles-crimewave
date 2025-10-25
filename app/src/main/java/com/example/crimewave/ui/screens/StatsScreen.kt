package com.example.crimewave.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.crimewave.data.model.ProductType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Estadísticas de Ventas") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Resumen de Ventas",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(getSalesStatCards()) { stat ->
                        StatCard(
                            title = stat.title,
                            value = stat.value,
                            icon = stat.icon,
                            color = stat.color
                        )
                    }
                }
            }

            item {
                Card {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 12.dp)
                        ) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Ventas por Categoría",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        getCategoryStats().forEach { (category, sales) ->
                            CategoryStatRow(
                                category = category,
                                sales = sales,
                                total = 125 // Total de ventas
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }

            item {
                Card {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 12.dp)
                        ) {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Productos Más Vendidos",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        getTopProductStats().forEach { (product, sales) ->
                            ProductStatRow(product = product, sales = sales)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    icon: @Composable () -> Unit,
    color: Color
) {
    Card(
        modifier = Modifier.width(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            icon()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun CategoryStatRow(
    category: ProductType,
    sales: Int,
    total: Int
) {
    val percentage = (sales.toFloat() / total * 100).toInt()
    val color = when (category) {
        ProductType.POLERAS -> Color(0xFF4CAF50)
        ProductType.POLERONES -> Color(0xFF2196F3)
        ProductType.CUADROS -> Color(0xFFFF9800)
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = getCategoryText(category),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "$sales ventas ($percentage%)",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }

        LinearProgressIndicator(
            progress = { percentage / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            color = color,
        )
    }
}

@Composable
private fun ProductStatRow(
    product: String,
    sales: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = product,
            style = MaterialTheme.typography.bodyMedium
        )
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = "$sales vendidos",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
            )
        }
    }
}

private fun getCategoryText(category: ProductType): String {
    return when (category) {
        ProductType.POLERAS -> "👕 Poleras"
        ProductType.POLERONES -> "🧥 Polerones"
        ProductType.CUADROS -> "🖼️ Cuadros"
    }
}

private data class StatCardData(
    val title: String,
    val value: String,
    val icon: @Composable () -> Unit,
    val color: Color
)

private fun getSalesStatCards(): List<StatCardData> {
    return listOf(
        StatCardData(
            title = "Ventas Total",
            value = "150",
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
            color = Color(0xFF2196F3)
        ),
        StatCardData(
            title = "Este Mes",
            value = "48",
            icon = { Icon(Icons.Default.Info, contentDescription = null) },
            color = Color(0xFF4CAF50)
        ),
        StatCardData(
            title = "Ingresos",
            value = "$3,850",
            icon = { Icon(Icons.Default.Info, contentDescription = null) },
            color = Color(0xFFFF9800)
        )
    )
}

private fun getCategoryStats(): List<Pair<ProductType, Int>> {
    return listOf(
        ProductType.POLERAS to 65,
        ProductType.POLERONES to 32,
        ProductType.CUADROS to 28
    )
}

private fun getTopProductStats(): List<Pair<String, Int>> {
    return listOf(
        "Polera Araña Edición Especial" to 24,
        "Polerón Anime Premium" to 18,
        "Cuadro Jujutsu Kaisen" to 15,
        "Polera Chainsaw Man" to 11,
        "Polerón Attack on Titan" to 9
    )
}
