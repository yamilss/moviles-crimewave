package com.example.crimewave.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.crimewave.data.model.*

class CartViewModel : ViewModel() {
    private val _cartState = mutableStateOf(Cart())
    val cartState = _cartState

    private val _lastOrder = mutableStateOf<Order?>(null)
    val lastOrder = _lastOrder

    fun addToCart(clothingItem: ClothingItem, selectedSize: String, quantity: Int = 1) {
        val currentItems = _cartState.value.items.toMutableList()

        // Verificar si el item ya existe en el carrito
        val existingItemIndex = currentItems.indexOfFirst {
            it.clothingItem.id == clothingItem.id && it.selectedSize == selectedSize
        }

        if (existingItemIndex >= 0) {
            // Si existe, actualizar la cantidad
            val existingItem = currentItems[existingItemIndex]
            currentItems[existingItemIndex] = existingItem.copy(
                quantity = existingItem.quantity + quantity
            )
        } else {
            // Si no existe, agregar nuevo item
            val cartItem = CartItem(
                id = "${clothingItem.id}_${selectedSize}_${System.currentTimeMillis()}",
                clothingItem = clothingItem,
                quantity = quantity,
                selectedSize = selectedSize
            )
            currentItems.add(cartItem)
        }

        _cartState.value = Cart(items = currentItems)
    }

    fun removeFromCart(cartItemId: String) {
        val currentItems = _cartState.value.items.filter { it.id != cartItemId }
        _cartState.value = Cart(items = currentItems)
    }

    fun updateQuantity(cartItemId: String, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(cartItemId)
            return
        }

        val currentItems = _cartState.value.items.toMutableList()
        val itemIndex = currentItems.indexOfFirst { it.id == cartItemId }

        if (itemIndex >= 0) {
            currentItems[itemIndex] = currentItems[itemIndex].copy(quantity = newQuantity)
            _cartState.value = Cart(items = currentItems)
        }
    }

    fun clearCart() {
        _cartState.value = Cart()
    }

    fun checkout(): Order {
        val cart = _cartState.value
        val totalAmount = cart.totalAmount

        // Rechazar SOLO si el monto supera los 100,000 CLP
        val status = if (totalAmount > 100000) {
            OrderStatus.REJECTED
        } else {
            OrderStatus.APPROVED
        }

        val order = Order(
            id = "ORDER_${System.currentTimeMillis()}",
            items = cart.items,
            totalAmount = totalAmount,
            status = status,
            rejectionReason = if (status == OrderStatus.REJECTED)
                "Monto insuficiente. Pruebe con otro medio de pago"
            else null
        )

        _lastOrder.value = order

        // Si la orden fue aprobada, limpiar el carrito
        if (status == OrderStatus.APPROVED) {
            clearCart()
        }

        return order
    }

    fun getCartItemCount(): Int = _cartState.value.totalItems

    fun getCartTotal(): Double = _cartState.value.totalAmount
}
