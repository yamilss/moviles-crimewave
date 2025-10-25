package com.example.crimewave.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.crimewave.data.model.ProductType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    onNavigateBack: () -> Unit,
    onReportSubmitted: () -> Unit
) {
    var productName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(ProductType.POLERAS) }
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar Producto") },
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
            Card {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Información del Producto",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = productName,
                        onValueChange = { productName = it },
                        label = { Text("Nombre del producto") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Descripción detallada") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 5
                    )

                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("Precio ($)") },
                        modifier = Modifier.fillMaxWidth(),
                        prefix = { Text("$") }
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = getCategoryText(selectedCategory),
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Categoría") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            ProductType.entries.forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(getCategoryText(category)) },
                                    onClick = {
                                        selectedCategory = category
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Card {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Información Adicional",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "• Asegúrate de proporcionar información precisa del producto\n• Incluye una descripción detallada que ayude a los clientes\n• El precio debe incluir impuestos si corresponde",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    // Aquí se agregaría el producto
                    onReportSubmitted()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = productName.isNotBlank() && description.isNotBlank() && price.isNotBlank()
            ) {
                Text("Agregar Producto")
            }
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
