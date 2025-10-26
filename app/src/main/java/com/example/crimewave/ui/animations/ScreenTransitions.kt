package com.example.crimewave.ui.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun AnimatedScreenTransition(
    targetScreen: String,
    @Suppress("UNUSED_PARAMETER") previousScreen: String,
    content: @Composable () -> Unit
) {
    // Animación de slide horizontal suave
    AnimatedContent(
        targetState = targetScreen,
        transitionSpec = {
            // Slide hacia la derecha para navegación hacia adelante
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(500, easing = FastOutSlowInEasing)
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(500, easing = FastOutSlowInEasing)
            )
        },
        modifier = Modifier.fillMaxSize(),
        label = "slide_transition",
        content = { screenState ->
            // screenState contiene el estado actual de la pantalla
            // pero usamos content() directamente ya que maneja su propio estado
            screenState // Usar el parámetro para evitar warning
            content()
        }
    )
}
