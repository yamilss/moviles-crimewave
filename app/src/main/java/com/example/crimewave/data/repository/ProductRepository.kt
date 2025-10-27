package com.example.crimewave.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.crimewave.data.model.ClothingItem
import com.example.crimewave.data.model.ProductType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductRepository(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("product_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val KEY_PRODUCTS = "products"
    }

    init {
        // Inicializar productos por defecto si no existen
        if (getProducts().isEmpty()) {
            initializeDefaultProducts()
        }
    }

    private fun initializeDefaultProducts() {
        val defaultProducts = listOf(
            ClothingItem(
                id = "1",
                name = "Polera Satoru Gojo",
                description = "Diseño original de Satoru Gojo del anime Jujutsu Kaisen",
                price = 22000.0,
                imageUrl = "satorupolera",
                category = ProductType.POLERAS,
                isNew = true,
                sizes = listOf("S", "M", "L", "XL")
            ),
            ClothingItem(
                id = "2",
                name = "Polerón Toga Himiko",
                description = "Polerón con diseño de Himiko Toga del anime My Hero Academia",
                price = 42000.0,
                imageUrl = "togahoodie",
                category = ProductType.POLERONES,
                isNew = true,
                isFeatured = true,
                sizes = listOf("S", "M", "L", "XL")
            ),
            ClothingItem(
                id = "3",
                name = "Cuadro Given",
                description = "Cuadro decorativo minimalista con diseño del anime Given",
                price = 45000.0,
                imageUrl = "givencuadro",
                category = ProductType.CUADROS,
                isNew = true,
                sizes = listOf("30x39", "40x50", "50x70", "70x81")
            )
        )
        saveProducts(defaultProducts)
    }

    fun getProducts(): List<ClothingItem> {
        val productsJson = sharedPreferences.getString(KEY_PRODUCTS, "[]")
        val type = object : TypeToken<List<ClothingItem>>() {}.type
        return gson.fromJson(productsJson, type) ?: emptyList()
    }

    private fun saveProducts(products: List<ClothingItem>) {
        val productsJson = gson.toJson(products)
        sharedPreferences.edit()
            .putString(KEY_PRODUCTS, productsJson)
            .apply()
    }

    fun addProduct(product: ClothingItem): Boolean {
        val currentProducts = getProducts().toMutableList()

        // Verificar si el producto ya existe
        if (currentProducts.any { it.id == product.id }) {
            return false
        }

        currentProducts.add(product)
        saveProducts(currentProducts)
        return true
    }

    fun updateProduct(product: ClothingItem): Boolean {
        val products = getProducts().toMutableList()
        val index = products.indexOfFirst { it.id == product.id }

        return if (index >= 0) {
            products[index] = product
            saveProducts(products)
            true
        } else {
            false
        }
    }

    fun deleteProduct(productId: String): Boolean {
        val products = getProducts().toMutableList()
        val initialSize = products.size
        products.removeAll { it.id == productId }

        return if (products.size < initialSize) {
            saveProducts(products)
            true
        } else {
            false
        }
    }

    fun getProductById(id: String): ClothingItem? {
        return getProducts().find { it.id == id }
    }

    fun getProductsByCategory(category: ProductType): List<ClothingItem> {
        return getProducts().filter { it.category == category }
    }

    fun getFeaturedProducts(): List<ClothingItem> {
        return getProducts().filter { it.isFeatured }
    }

    fun getNewProducts(): List<ClothingItem> {
        return getProducts().filter { it.isNew }
    }
}
