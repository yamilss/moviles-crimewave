package com.example.crimewave.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crimewave.R
import com.example.crimewave.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val authState by authViewModel.authState

    // Validaciones en tiempo real
    val isValidEmail = email.trim().isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
    val isValidPhone = phoneNumber.length == 9 && phoneNumber.all { it.isDigit() }
    val isValidPassword = password.length >= 4
    val passwordsMatch = password == confirmPassword
    val isValidForm = isValidEmail && isValidPhone && isValidPassword && passwordsMatch

    // Efectos secundarios para manejar el éxito del registro
    LaunchedEffect(authState.isAuthenticated) {
        if (authState.isAuthenticated) {
            onRegisterSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE8E8F0),
                        Color(0xFFD0D0E0)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF4A4A5A)
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Imagen de logo arriba del título
                Image(
                    painter = painterResource(id = R.drawable.logodefault2),
                    contentDescription = "crimewave banner",
                    modifier = Modifier
                        .width(250.dp)
                        .height(100.dp)
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.Fit
                )

                // Título
                Text(
                    text = "CREAR UNA\nCUENTA SIGMA",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Línea decorativa
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF6366F1),
                                    Color(0xFF8B5CF6)
                                )
                            )
                        )
                        .padding(bottom = 32.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Campo E-mail
                Column {
                    Text(
                        text = "E-mail*",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            authViewModel.clearError()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6366F1),
                            unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.White
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Campo Número
                Column {
                    Text(
                        text = "Número (9 dígitos)",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { newValue ->
                            if (newValue.length <= 9 && newValue.all { it.isDigit() }) {
                                phoneNumber = newValue
                                authViewModel.clearError()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6366F1),
                            unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.White
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Campo Contraseña
                Column {
                    Text(
                        text = "Contraseña",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            authViewModel.clearError()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6366F1),
                            unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.White
                        ),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Campo Confirmar Contraseña
                Column {
                    Text(
                        text = "Confirmar Contraseña",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            authViewModel.clearError()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6366F1),
                            unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.White
                        ),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Botón REGISTRARSE
                Button(
                    onClick = {
                        if (isValidForm) {
                            authViewModel.register(
                                email.trim(),
                                phoneNumber.trim(),
                                password.trim(),
                                confirmPassword.trim()
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isValidForm,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Gray.copy(alpha = 0.3f)
                    ),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF6366F1),
                                        Color(0xFF8B5CF6)
                                    )
                                ),
                                shape = RoundedCornerShape(25.dp)
                            )
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "REGISTRARSE",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Error message
                authState.error?.let { error ->
                    Text(
                        text = error,
                        color = Color(0xFFEF4444),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                // Enlace de login
                Row {
                    Text(
                        text = "¿Ya tienes cuenta? Inicia sesión ",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                    ClickableText(
                        text = AnnotatedString("aquí"),
                        onClick = { onNavigateToLogin() },
                        style = LocalTextStyle.current.copy(
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold

                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
