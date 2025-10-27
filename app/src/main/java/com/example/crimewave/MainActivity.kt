package com.example.crimewave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.crimewave.navigation.NavigationHost
import com.example.crimewave.ui.theme.CrimewaveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CrimewaveTheme {
                NavigationHost(
                    onExitApp = { finish() }
                )
            }
        }
    }
}


