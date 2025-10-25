package com.example.crimewave.data.model

data class CrimeItem(
    val id: String,
    val title: String,
    val description: String,
    val location: String,
    val date: String,
    val severity: CrimeSeverity,
    val imageUrl: String? = null,
    val reported: Boolean = false
)

enum class CrimeSeverity {
    LOW, MEDIUM, HIGH, CRITICAL
}
