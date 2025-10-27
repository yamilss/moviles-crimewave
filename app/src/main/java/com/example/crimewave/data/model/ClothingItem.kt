package com.example.crimewave.data.model

data class ClothingItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String, // Main image for backward compatibility
    val imageUrls: List<String> = emptyList(), // Multiple images, empty by default for backward compatibility
    val category: ProductType,
    val isNew: Boolean = false,
    val isFeatured: Boolean = false,
    val specialOffer: String? = null,
    val sizes: List<String> = listOf("S", "M", "L", "XL"),
    val stock: Int = 10,
    val reviewCount: Int = 0
) {
    // Helper property to ensure we always have at least one image
    val allImages: List<String>
        get() = try {
            when {
                imageUrls.isNotEmpty() && imageUrls.size >= 3 -> imageUrls.take(3)
                imageUrls.isNotEmpty() -> {
                    // Si hay algunas imágenes pero menos de 3, completar con variaciones
                    val result = imageUrls.toMutableList()
                    while (result.size < 3) {
                        when (result.size) {
                            1 -> result.add("placeholder1")
                            2 -> result.add("placerholder2")
                        }
                    }
                    result.take(3)
                }
                imageUrl.isNotBlank() -> {
                    // Generate 3 images from the main imageUrl for backward compatibility
                    listOf(imageUrl, "placeholder1", "placerholder2")
                }
                else -> {
                    // Fallback completo
                    listOf("default_product", "placeholder1", "placerholder2")
                }
            }
        } catch (e: Exception) {
            // En caso de cualquier error, usar fallback seguro
            listOf("default_product", "default_product_2", "default_product_3")
        }
}

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
