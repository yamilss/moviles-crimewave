package com.example.crimewave.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Función para validar celular de 9 dígitos
private fun validateCelular(celular: String): Boolean {
    return celular.length == 9 && celular.all { it.isDigit() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShippingAddressScreen(
    onNavigateBack: () -> Unit,
    authViewModel: com.example.crimewave.ui.viewmodel.AuthViewModel
) {
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var codigoPostal by remember { mutableStateOf("") }
    var pais by remember { mutableStateOf("Chile") }
    var region by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }
    var rutDni by remember { mutableStateOf("") }
    var instagram by remember { mutableStateOf("") }

    var paisExpanded by remember { mutableStateOf(false) }
    var regionExpanded by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    // Validación para celular
    val isValidCelular = celular.isEmpty() || validateCelular(celular)

    val authState by authViewModel.authState

    // Cargar datos existentes si los hay
    LaunchedEffect(authState.currentUser?.shippingAddress) {
        authState.currentUser?.shippingAddress?.let { address ->
            nombre = address.nombre
            apellidos = address.apellidos
            direccion = address.direccion
            rut = address.rut
            ciudad = address.ciudad
            codigoPostal = address.codigoPostal
            pais = address.pais
            region = address.region
            celular = address.celular
            instagram = address.instagram
        }
    }

    val paises = listOf("Chile")
    val regiones = listOf(
        "Seleccionar región",
        "Región de Arica y Parinacota",
        "Región de Tarapacá",
        "Región de Antofagasta",
        "Región de Atacama",
        "Región de Coquimbo",
        "Región de Valparaíso",
        "Región Metropolitana de Santiago",
        "Región del Libertador General Bernardo O'Higgins",
        "Región del Maule",
        "Región de Ñuble",
        "Región del Biobío",
        "Región de La Araucanía",
        "Región de Los Ríos",
        "Región de Los Lagos",
        "Región Aysén del General Carlos Ibáñez del Campo",
        "Región de Magallanes y de la Antártica Chilena"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 48.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.Black
                )
            }
        }

        // Título
        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Añadir Dirección de Envío",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Fila 1: Nombre y Apellidos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Nombre *",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Apellidos *",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = apellidos,
                        onValueChange = { apellidos = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Fila 2: Dirección y RUT
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Dirección *",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = direccion,
                        onValueChange = { direccion = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "RUT *",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = rut,
                        onValueChange = { rut = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Fila 3: Ciudad y Código Postal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Ciudad *",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = ciudad,
                        onValueChange = { ciudad = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Código postal",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = codigoPostal,
                        onValueChange = { codigoPostal = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Fila 4: País y Región
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "País *",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    ExposedDropdownMenuBox(
                        expanded = paisExpanded,
                        onExpandedChange = { paisExpanded = !paisExpanded }
                    ) {
                        OutlinedTextField(
                            value = pais,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = paisExpanded) },
                            modifier = Modifier
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Gray,
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = paisExpanded,
                            onDismissRequest = { paisExpanded = false }
                        ) {
                            paises.forEach { paisItem ->
                                DropdownMenuItem(
                                    text = { Text(paisItem) },
                                    onClick = {
                                        pais = paisItem
                                        paisExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Región *",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    ExposedDropdownMenuBox(
                        expanded = regionExpanded,
                        onExpandedChange = { regionExpanded = !regionExpanded }
                    ) {
                        OutlinedTextField(
                            value = region.ifEmpty { "Seleccionar región" },
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = regionExpanded) },
                            modifier = Modifier
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Gray,
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = regionExpanded,
                            onDismissRequest = { regionExpanded = false }
                        ) {
                            regiones.forEach { regionItem ->
                                DropdownMenuItem(
                                    text = { Text(regionItem) },
                                    onClick = {
                                        region = regionItem
                                        regionExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Fila 5: Celular y Rut (DNI)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Celular",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = celular,
                        onValueChange = { newValue ->
                            // Solo permitir números y máximo 9 dígitos
                            if (newValue.all { it.isDigit() } && newValue.length <= 9) {
                                celular = newValue
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        isError = celular.isNotEmpty() && !isValidCelular,
                        supportingText = if (celular.isNotEmpty() && !isValidCelular) {
                            { Text("Debe ser exactamente 9 dígitos", color = MaterialTheme.colorScheme.error, fontSize = 12.sp) }
                        } else null,
                        placeholder = { Text("987654321", color = Color.Gray.copy(alpha = 0.6f)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (isValidCelular) Color.Gray else MaterialTheme.colorScheme.error,
                            unfocusedBorderColor = if (isValidCelular) Color.Gray.copy(alpha = 0.5f) else MaterialTheme.colorScheme.error,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            // Instagram
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "INSTAGRAM",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = instagram,
                    onValueChange = { instagram = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // Botón Confirmar
                Button(
                    onClick = {
                        val success = authViewModel.saveShippingAddress(
                            nombre = nombre,
                            apellidos = apellidos,
                            direccion = direccion,
                            rut = rut,
                            ciudad = ciudad,
                            codigoPostal = codigoPostal,
                            pais = pais,
                            region = region,
                            celular = celular,
                            instagram = instagram
                        )
                        if (success) {
                            showSuccessDialog = true
                        }
                    },
                    modifier = Modifier.weight(2f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7C4DFF)
                    ),
                    shape = RoundedCornerShape(25.dp),
                    enabled = nombre.isNotBlank() && apellidos.isNotBlank() &&
                             direccion.isNotBlank() && rut.isNotBlank() &&
                             ciudad.isNotBlank() && region.isNotBlank() && isValidCelular
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("CONFIRMAR", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // Diálogo de éxito
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
                onNavigateBack()
            },
            title = { Text("✅ Dirección Guardada") },
            text = {
                Column {
                    Text("La dirección de envío se ha guardado correctamente.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Detalles guardados:", fontWeight = FontWeight.Bold)
                    Text("• $nombre $apellidos")
                    Text("• $direccion")
                    Text("• $ciudad, $region")
                    if (celular.isNotEmpty()) Text("• Teléfono: $celular")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        onNavigateBack()
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}
