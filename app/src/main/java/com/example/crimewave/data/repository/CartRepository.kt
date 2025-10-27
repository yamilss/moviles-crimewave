package com.example.crimewave.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.crimewave.data.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartRepository(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val KEY_CART_ITEMS = "cart_items"
        private const val KEY_LAST_ORDER = "last_order"
        private const val KEY_ORDER_HISTORY = "order_history"
        private const val MAX_ORDER_HISTORY = 20
    }

    // Variable para almacenar el email del usuario actual
    private var currentUserEmail: String? = null

    // Función para establecer el usuario actual
    fun setCurrentUser(email: String?) {
        currentUserEmail = email
    }

    // Función para obtener la clave específica del usuario
    private fun getUserKey(baseKey: String): String {
        return if (currentUserEmail != null) {
            "${baseKey}_${currentUserEmail}"
        } else {
            baseKey
        }
    }

    fun getCart(): Cart {
        // Si no hay usuario establecido, devolver carrito vacío
        if (currentUserEmail == null) {
            return Cart()
        }

        val json = sharedPreferences.getString(getUserKey(KEY_CART_ITEMS), null)
        return if (json != null) {
            val type = object : TypeToken<List<CartItem>>() {}.type
            val items: List<CartItem> = gson.fromJson(json, type)
            Cart(items = items)
        } else {
            Cart()
        }
    }

    private fun saveCart(cart: Cart) {
        // Solo guardar si hay usuario establecido
        if (currentUserEmail != null) {
            val json = gson.toJson(cart.items)
            sharedPreferences.edit()
                .putString(getUserKey(KEY_CART_ITEMS), json)
                .commit()
        }
    }

    fun addItemToCart(cartItem: CartItem) {
        val currentCart = getCart()
        val currentItems = currentCart.items.toMutableList()

        // Verificar si el item ya existe en el carrito
        val existingItemIndex = currentItems.indexOfFirst {
            it.clothingItem.id == cartItem.clothingItem.id && it.selectedSize == cartItem.selectedSize
        }

        if (existingItemIndex >= 0) {
            // Si existe, actualizar la cantidad
            val existingItem = currentItems[existingItemIndex]
            currentItems[existingItemIndex] = existingItem.copy(
                quantity = existingItem.quantity + cartItem.quantity
            )
        } else {
            // Si no existe, agregar nuevo item
            currentItems.add(cartItem)
        }

        saveCart(Cart(items = currentItems))
    }

    fun removeItemFromCart(cartItemId: String) {
        val currentCart = getCart()
        val updatedItems = currentCart.items.filter { it.id != cartItemId }
        saveCart(Cart(items = updatedItems))
    }

    fun updateItemQuantity(cartItemId: String, newQuantity: Int) {
        val currentCart = getCart()
        val updatedItems = currentCart.items.map { item ->
            if (item.id == cartItemId) {
                item.copy(quantity = newQuantity)
            } else {
                item
            }
        }
        saveCart(Cart(items = updatedItems))
    }

    fun clearCart() {
        if (currentUserEmail != null) {
            sharedPreferences.edit()
                .remove(getUserKey(KEY_CART_ITEMS))
                .commit()
        }
    }

    fun saveLastOrder(order: Order) {
        if (currentUserEmail != null) {
            // Guardar la última orden
            val orderJson = gson.toJson(order)
            sharedPreferences.edit()
                .putString(getUserKey(KEY_LAST_ORDER), orderJson)
                .commit()

            // Agregar al historial
            addToOrderHistory(order)
        }
    }

    fun getLastOrder(): Order? {
        if (currentUserEmail == null) return null

        val json = sharedPreferences.getString(getUserKey(KEY_LAST_ORDER), null)
        return if (json != null) {
            gson.fromJson(json, Order::class.java)
        } else {
            null
        }
    }

    private fun addToOrderHistory(order: Order) {
        if (currentUserEmail == null) return

        val currentHistory = getOrderHistory().toMutableList()

        // Agregar la nueva orden al principio
        currentHistory.add(0, order)

        // Mantener solo las últimas 20 órdenes
        if (currentHistory.size > MAX_ORDER_HISTORY) {
            currentHistory.removeAt(currentHistory.size - 1)
        }

        // Guardar el historial actualizado
        val historyJson = gson.toJson(currentHistory)
        sharedPreferences.edit()
            .putString(getUserKey(KEY_ORDER_HISTORY), historyJson)
            .commit()
    }

    fun getOrderHistory(): List<Order> {
        if (currentUserEmail == null) return emptyList()

        val json = sharedPreferences.getString(getUserKey(KEY_ORDER_HISTORY), null)
        return if (json != null) {
            val type = object : TypeToken<List<Order>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }
}
