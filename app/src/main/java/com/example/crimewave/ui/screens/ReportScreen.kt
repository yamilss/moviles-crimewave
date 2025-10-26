package com.example.crimewave.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crimewave.data.model.ProductType
import com.example.crimewave.data.model.ClothingItem
import com.example.crimewave.ui.viewmodel.ClothingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    clothingViewModel: ClothingViewModel,
    onNavigateBack: () -> Unit,
    onReportSubmitted: () -> Unit
) {
    var productName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(ProductType.POLERAS) }
    var expanded by remember { mutableStateOf(false) }
    var selectedSize by remember { mutableStateOf("S") }
    var sizeExpanded by remember { mutableStateOf(false) }
    var sizeQuantity by remember { mutableStateOf("1") }
    var selectedSizes by remember { mutableStateOf(mutableMapOf<String, Int>()) }
    var selectedImage by remember { mutableStateOf<String?>(null) }

    // Validaciones
    val isValidPrice = price.toDoubleOrNull()?.let { it >= 15000 } ?: false
    val isValidStock = stock.toIntOrNull()?.let { it >= 0 } ?: false
    val isValidSizeQuantity = sizeQuantity.toIntOrNull()?.let { it > 0 } ?: false

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE8EAF6),
                        Color(0xFFF3E5F5)
                    )
                )
            )
    ) {
        // Header azul con gradiente
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF2196F3),
                            Color(0xFF3F51B5)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            Color.White.copy(alpha = 0.2f),
                            RoundedCornerShape(20.dp)
                        )
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))


                Spacer(modifier = Modifier.width(30.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Agregar Nuevo Producto",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Completa la información del producto que deseas agregar al inventario",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 13.sp
                    )
                }


                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        // Banner informativo con gradiente
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFE3F2FD),
                            Color(0xFFF3E5F5)
                        )
                    )
                )
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = null,
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Agregando como: Administrador(Administrador)",
                    color = Color(0xFF1976D2),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Formulario principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Fila 1: Nombre del Producto y Precio
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Nombre del Producto
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .background(Color(0xFF4CAF50), RoundedCornerShape(2.dp))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Nombre del Producto *",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF424242)
                        )
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        OutlinedTextField(
                            value = productName,
                            onValueChange = { productName = it },
                            placeholder = { Text("Ej. Polera Anime Naruto", color = Color.Gray.copy(alpha = 0.6f)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF2196F3),
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            )
                        )
                    }
                }

                // Precio
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Precio * (Min: $15,000)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF424242)
                        )
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        OutlinedTextField(
                            value = price,
                            onValueChange = { newPrice ->
                                // Solo permitir números
                                if (newPrice.all { it.isDigit() || it == '.' }) {
                                    price = newPrice
                                }
                            },
                            placeholder = { Text("15000", color = Color.Gray.copy(alpha = 0.6f)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            isError = price.isNotEmpty() && !isValidPrice,
                            supportingText = if (price.isNotEmpty() && !isValidPrice) {
                                { Text("El precio mínimo es $15,000 CLP", color = MaterialTheme.colorScheme.error) }
                            } else null,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (isValidPrice || price.isEmpty()) Color(0xFF2196F3) else MaterialTheme.colorScheme.error,
                                unfocusedBorderColor = if (isValidPrice || price.isEmpty()) Color.Gray.copy(alpha = 0.3f) else MaterialTheme.colorScheme.error,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            )
                        )
                    }
                }
            }

            // Fila 2: Categoría y Stock Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Categoría
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .background(Color(0xFF2196F3), RoundedCornerShape(2.dp))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Categoría *",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF424242)
                        )
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                value = getCategoryDisplayText(selectedCategory),
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier
                                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                    .fillMaxWidth()
                                    .background(Color.White),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF2196F3),
                                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White
                                )
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                ProductType.entries.forEach { category ->
                                    DropdownMenuItem(
                                        text = { Text(getCategoryDisplayText(category)) },
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

                // Stock Total
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .background(Color(0xFFFF9800), RoundedCornerShape(2.dp))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Stock Total",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF424242)
                        )
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        OutlinedTextField(
                            value = stock,
                            onValueChange = { newStock ->
                                // Solo permitir números enteros no negativos
                                if (newStock.all { it.isDigit() } && (newStock.isEmpty() || newStock.toIntOrNull() != null)) {
                                    stock = newStock
                                }
                            },
                            placeholder = { Text("50", color = Color.Gray.copy(alpha = 0.6f)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            isError = stock.isNotEmpty() && !isValidStock,
                            supportingText = if (stock.isNotEmpty() && !isValidStock) {
                                { Text("El stock no puede ser negativo", color = MaterialTheme.colorScheme.error) }
                            } else null,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (isValidStock || stock.isEmpty()) Color(0xFF2196F3) else MaterialTheme.colorScheme.error,
                                unfocusedBorderColor = if (isValidStock || stock.isEmpty()) Color.Gray.copy(alpha = 0.3f) else MaterialTheme.colorScheme.error,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            )
                        )
                    }
                }
            }

            // Descripción
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .background(Color(0xFF9C27B0), RoundedCornerShape(2.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Descripción *",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF424242)
                    )
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        placeholder = { Text("Describe el producto, sus características, materiales, etc", color = Color.Gray.copy(alpha = 0.6f)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .background(Color.White),
                        maxLines = 5,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF2196F3),
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                }
            }

            // Tallas y Cantidades
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .background(Color(0xFFE91E63), RoundedCornerShape(2.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Tallas y Cantidades",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF424242)
                    )
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ExposedDropdownMenuBox(
                            expanded = sizeExpanded,
                            onExpandedChange = { sizeExpanded = !sizeExpanded },
                            modifier = Modifier.width(80.dp)
                        ) {
                            OutlinedTextField(
                                value = selectedSize,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = sizeExpanded) },
                                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF2196F3),
                                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f)
                                )
                            )

                            ExposedDropdownMenu(
                                expanded = sizeExpanded,
                                onDismissRequest = { sizeExpanded = false }
                            ) {
                                listOf("XS", "S", "M", "L", "XL", "XXL").forEach { size ->
                                    DropdownMenuItem(
                                        text = { Text(size) },
                                        onClick = {
                                            selectedSize = size
                                            sizeExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        OutlinedTextField(
                            value = sizeQuantity,
                            onValueChange = { newQuantity ->
                                // Solo permitir números enteros positivos
                                if (newQuantity.all { it.isDigit() } && (newQuantity.isEmpty() || newQuantity.toIntOrNull()?.let { it > 0 } == true)) {
                                    sizeQuantity = newQuantity
                                } else if (newQuantity.isEmpty()) {
                                    sizeQuantity = newQuantity
                                }
                            },
                            modifier = Modifier.width(120.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            isError = sizeQuantity.isNotEmpty() && !isValidSizeQuantity,
                            placeholder = { Text("1", color = Color.Gray.copy(alpha = 0.6f)) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (isValidSizeQuantity || sizeQuantity.isEmpty()) Color(0xFF2196F3) else MaterialTheme.colorScheme.error,
                                unfocusedBorderColor = if (isValidSizeQuantity || sizeQuantity.isEmpty()) Color.Gray.copy(alpha = 0.3f) else MaterialTheme.colorScheme.error
                            )
                        )

                        Button(
                            onClick = {
                                val quantity = sizeQuantity.toIntOrNull()
                                if (quantity != null && quantity > 0 && selectedSize.isNotEmpty()) {
                                    selectedSizes = selectedSizes.toMutableMap().apply {
                                        this[selectedSize] = quantity
                                    }
                                    sizeQuantity = "1" // Reset quantity field
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2196F3)
                            ),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier.size(48.dp),
                            enabled = isValidSizeQuantity && selectedSize.isNotEmpty()
                        ) {
                            Text("+", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Mostrar tallas seleccionadas
                    if (selectedSizes.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tallas agregadas:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF424242)
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            selectedSizes.forEach { (size, quantity) ->
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                                    modifier = Modifier.wrapContentSize()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "$size: $quantity",
                                            fontSize = 12.sp,
                                            color = Color(0xFF1976D2)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        IconButton(
                                            onClick = {
                                                selectedSizes = selectedSizes.toMutableMap().apply {
                                                    remove(size)
                                                }
                                            },
                                            modifier = Modifier.size(16.dp)
                                        ) {
                                            Text("×", color = Color(0xFF1976D2), fontSize = 14.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Imagen del Producto
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .background(Color(0xFF607D8B), RoundedCornerShape(2.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Imagen del Producto",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF424242)
                    )
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedButton(
                                onClick = {
                                    // Simulamos selección de imagen predeterminada basada en categoría
                                    selectedImage = when (selectedCategory) {
                                        ProductType.POLERAS -> "polera_default"
                                        ProductType.POLERONES -> "poleron_default"
                                        ProductType.CUADROS -> "cuadro_default"
                                    }
                                },
                                modifier = Modifier.padding(end = 12.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = Color(0xFF424242)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Examinar...")
                            }

                            Column {
                                Text(
                                    text = if (selectedImage != null) "Imagen seleccionada: ${selectedImage!!}" else "No se ha seleccionado ningún archivo",
                                    color = if (selectedImage != null) Color(0xFF4CAF50) else Color.Gray,
                                    fontSize = 13.sp
                                )

                                if (selectedImage != null) {
                                    TextButton(
                                        onClick = { selectedImage = null },
                                        modifier = Modifier.padding(0.dp)
                                    ) {
                                        Text(
                                            text = "Quitar imagen",
                                            color = Color(0xFFFF5722),
                                            fontSize = 11.sp
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Si no seleccionas una imagen, se usará una imagen predeterminada según la categoría.",
                            color = Color.Gray.copy(alpha = 0.8f),
                            fontSize = 11.sp,
                            lineHeight = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF616161)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        Color(0xFF616161).copy(alpha = 0.5f)
                    )
                ) {
                    Text("CANCELAR", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }

                Button(
                    onClick = {
                        // Crear nuevo producto
                        val newProduct = ClothingItem(
                            id = clothingViewModel.generateNextProductId(),
                            name = productName,
                            description = description,
                            price = price.toDoubleOrNull() ?: 0.0,
                            imageUrl = "default_product",
                            category = selectedCategory,
                            isNew = true,
                            isFeatured = false,
                            stock = stock.toIntOrNull() ?: 10
                        )

                        // Agregar al ViewModel
                        clothingViewModel.addProduct(newProduct)
                        onReportSubmitted()
                    },
                    modifier = Modifier.weight(2f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    ),
                    enabled = productName.isNotBlank() &&
                             description.isNotBlank() &&
                             price.isNotBlank() &&
                             isValidPrice &&
                             (stock.isEmpty() || isValidStock),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("AGREGAR PRODUCTO", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

private fun getCategoryDisplayText(category: ProductType): String {
    return when (category) {
        ProductType.POLERAS -> "Poleras"
        ProductType.POLERONES -> "Polerones"
        ProductType.CUADROS -> "Cuadros"
    }
}
