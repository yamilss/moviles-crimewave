package com.example.crimewave.data.model

data class ClothingItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: ProductType,
    val isNew: Boolean = false,
    val isFeatured: Boolean = false,
    val specialOffer: String? = null,
    val sizes: List<String> = listOf("S", "M", "L", "XL"),
    val stock: Int = 10,
    val reviewCount: Int = 0
)

data class ClothingCategory(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String
)

enum class ProductType {
    POLERAS,
    POLERONES,
    CUADROS
}
