package com.example.crimewave.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.example.crimewave.data.model.ClothingItem
import com.example.crimewave.data.model.ClothingCategory
import com.example.crimewave.data.model.ProductType

class ClothingViewModel : ViewModel() {
    private val _products = mutableStateOf<List<ClothingItem>>(emptyList())
    val products: State<List<ClothingItem>> = _products

    private val _categories = mutableStateOf<List<ClothingCategory>>(emptyList())
    val categories: State<List<ClothingCategory>> = _categories

    private val _featuredProducts = mutableStateOf<List<ClothingItem>>(emptyList())
    val featuredProducts: State<List<ClothingItem>> = _featuredProducts

    private val _selectedProduct = mutableStateOf<ClothingItem?>(null)
    val selectedProduct: State<ClothingItem?> = _selectedProduct

    private val _currentVideoIndex = mutableStateOf(2)
    val currentVideoIndex: State<Int> = _currentVideoIndex

    private val _cartItems = mutableStateOf<List<ClothingItem>>(emptyList())
    val cartItems: State<List<ClothingItem>> = _cartItems

    init {
        loadSampleData()
        loadCategories()
        loadFeaturedProducts()
    }

    private fun loadSampleData() {
        _products.value = listOf(
            ClothingItem(
                id = "1",
                name = "Polera Araña",
                description = "Diseño exclusivo de araña con estilo anime",
                price = 25.99,
                imageUrl = "producto_arana",
                category = ProductType.POLERAS,
                isNew = true
            ),
            ClothingItem(
                id = "2",
                name = "Polera Original",
                description = "2 por $35 - Poleras básicas de alta calidad",
                price = 17.50,
                imageUrl = "producto_original",
                category = ProductType.POLERAS,
                isNew = false,
                specialOffer = "2 x $35"
            ),
            ClothingItem(
                id = "3",
                name = "Cuadro Anime",
                description = "Cuadros decorativos con diseños de anime",
                price = 45.00,
                imageUrl = "producto_cuadros",
                category = ProductType.CUADROS,
                isNew = true
            ),

            ClothingItem(
                id = "5",
                name = "Polera Satoru",
                description = "Diseño premium de Satoru Gojo",
                price = 29.99,
                imageUrl = "producto_satoru",
                category = ProductType.POLERAS,
                isNew = true,
                isFeatured = true
            ),
            ClothingItem(
                id = "6",
                name = "Polerón Anime",
                description = "Polerón con diseños anime exclusivos",
                price = 42.00,
                imageUrl = "producto_poleron",
                category = ProductType.POLERONES,
                isNew = true,
                isFeatured = true
            ),
            ClothingItem(
                id = "7",
                name = "Polera Anime Collection",
                description = "Colección especial de anime",
                price = 27.50,
                imageUrl = "polera_anime",
                category = ProductType.POLERAS,
                isNew = true
            ),
            ClothingItem(
                id = "8",
                name = "Jujutsu Kaisen Special",
                description = "Diseño exclusivo de Jujutsu Kaisen",
                price = 32.00,
                imageUrl = "jjk_product",
                category = ProductType.POLERAS,
                isNew = true,
                isFeatured = true
            )
        )
    }

    private fun loadCategories() {
        _categories.value = listOf(
            ClothingCategory(
                id = "cat1",
                name = "CATEGORÍA 1",
                description = "Diseños Especiales",
                imageUrl = "cat1"
            ),
            ClothingCategory(
                id = "cat2",
                name = "CATEGORÍA 2",
                description = "Poleras Premium",
                imageUrl = "cat2"
            ),
            ClothingCategory(
                id = "cat3",
                name = "CATEGORÍA 3",
                description = "Colección Limitada",
                imageUrl = "cat3"
            ),
            ClothingCategory(
                id = "cat4",
                name = "CATEGORÍA 4",
                description = "Cuadros Anime",
                imageUrl = "cat4"
            ),
            ClothingCategory(
                id = "cat5",
                name = "CATEGORÍA 5",
                description = "Anime Collection",
                imageUrl = "cat5"
            ),
            ClothingCategory(
                id = "cat8",
                name = "CATEGORÍA 8",
                description = "Jujutsu Kaisen",
                imageUrl = "cat8"
            )
        )
    }

    private fun loadFeaturedProducts() {
        _featuredProducts.value = _products.value.filter { it.isFeatured }
    }

    fun getProductById(id: String): ClothingItem? {
        return _products.value.find { it.id == id }
    }

    fun selectProduct(product: ClothingItem) {
        _selectedProduct.value = product
    }

    fun getProductsByCategory(category: ProductType): List<ClothingItem> {
        return _products.value.filter { it.category == category }
    }

    fun addToCart(product: ClothingItem) {
        _cartItems.value = _cartItems.value + product
    }

    fun removeFromCart(productId: String) {
        _cartItems.value = _cartItems.value.filter { it.id != productId }
    }

    fun getCartTotal(): Double {
        return _cartItems.value.sumOf { it.price }
    }

    fun navigateVideo(direction: String) {
        val videoCount = 5 // Total de videos disponibles
        if (direction == "left") {
            _currentVideoIndex.value = (_currentVideoIndex.value - 1 + videoCount) % videoCount
        } else {
            _currentVideoIndex.value = (_currentVideoIndex.value + 1) % videoCount
        }
    }

    fun getNewProducts(): List<ClothingItem> {
        return _products.value.filter { it.isNew }
    }

    fun searchProducts(query: String): List<ClothingItem> {
        return _products.value.filter {
            it.name.contains(query, ignoreCase = true) ||
            it.description.contains(query, ignoreCase = true)
        }
    }

    fun addProduct(product: ClothingItem) {
        // Si el producto tiene imagen por defecto, asignar la imagen predeterminada según la categoría
        val productWithImage = if (product.imageUrl == "default_product") {
            product.copy(imageUrl = getDefaultImageForCategory(product.category))
        } else {
            product
        }

        _products.value = _products.value + productWithImage
        loadFeaturedProducts() // Actualizar productos destacados
    }

    fun removeProduct(productId: String) {
        _products.value = _products.value.filter { it.id != productId }
        loadFeaturedProducts() // Actualizar productos destacados
    }

    fun updateProduct(updatedProduct: ClothingItem) {
        _products.value = _products.value.map { product ->
            if (product.id == updatedProduct.id) updatedProduct else product
        }
        loadFeaturedProducts() // Actualizar productos destacados
    }

    fun generateNextProductId(): String {
        val maxId = _products.value.mapNotNull { it.id.toIntOrNull() }.maxOrNull() ?: 0
        return (maxId + 1).toString()
    }

    // Función para obtener imagen predeterminada según la categoría
    fun getDefaultImageForCategory(category: ProductType): String {
        return when (category) {
            ProductType.POLERAS -> "satorupolera"
            ProductType.POLERONES -> "togahoodie"
            ProductType.CUADROS -> "givencuadro"
        }
    }
}
