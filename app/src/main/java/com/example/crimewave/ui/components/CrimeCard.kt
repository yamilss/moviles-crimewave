package com.example.crimewave.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.crimewave.data.model.ClothingItem
import com.example.crimewave.data.model.ProductType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(
    product: ClothingItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                if (product.isNew) {
                    NewChip()
                } else if (product.isFeatured) {
                    FeaturedChip()
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = getCategoryText(product.category),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            if (product.specialOffer != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "🏷️ ${product.specialOffer}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFFF5722)
                )
            }
        }
    }
}

@Composable
fun NewChip() {
    Surface(
        color = Color(0xFF4CAF50).copy(alpha = 0.1f),
        contentColor = Color(0xFF4CAF50),
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = "NUEVO",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun FeaturedChip() {
    Surface(
        color = Color(0xFF9C27B0).copy(alpha = 0.1f),
        contentColor = Color(0xFF9C27B0),
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = "DESTACADO",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

fun getCategoryText(category: ProductType): String {
    return when (category) {
        ProductType.POLERAS -> "👕 Poleras"
        ProductType.POLERONES -> "🧥 Polerones"
        ProductType.CUADROS -> "🖼️ Cuadros"
    }
}

