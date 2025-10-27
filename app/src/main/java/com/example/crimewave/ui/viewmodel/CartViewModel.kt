package com.example.crimewave.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.example.crimewave.data.model.*
import com.example.crimewave.data.repository.CartRepository
import java.util.UUID

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val cartRepository = CartRepository(application.applicationContext)

    private val _cartState = mutableStateOf(Cart())
    val cartState = _cartState

    private val _lastOrder = mutableStateOf<Order?>(null)
    val lastOrder = _lastOrder

    private var currentUser: User? = null

    init {
        // El carrito se iniciará vacío hasta que se establezca un usuario
        _cartState.value = Cart()
        _lastOrder.value = null
    }

    // Función para establecer el usuario actual y cargar su carrito
    fun setCurrentUser(user: User?) {
        currentUser = user
        if (user != null && !user.isAdmin) {
            // Solo los clientes (no admins) tienen carrito
            cartRepository.setCurrentUser(user.email)
            _cartState.value = cartRepository.getCart()
            _lastOrder.value = cartRepository.getLastOrder()
        } else {
            // Limpiar carrito para admin o usuario nulo
            cartRepository.setCurrentUser(null)
            _cartState.value = Cart()
            _lastOrder.value = null
        }
    }

    // Función para verificar si el usuario actual puede usar el carrito
    private fun canUseCart(): Boolean {
        return currentUser != null && !currentUser!!.isAdmin
    }

    fun addToCart(clothingItem: ClothingItem, selectedSize: String, quantity: Int = 1) {
        if (!canUseCart()) return

        val cartItem = CartItem(
            id = "${clothingItem.id}_${selectedSize}_${System.currentTimeMillis()}",
            clothingItem = clothingItem,
            quantity = quantity,
            selectedSize = selectedSize
        )

        cartRepository.addItemToCart(cartItem)
        _cartState.value = cartRepository.getCart()
    }

    fun removeFromCart(cartItemId: String) {
        if (!canUseCart()) return

        cartRepository.removeItemFromCart(cartItemId)
        _cartState.value = cartRepository.getCart()
    }

    fun updateQuantity(cartItemId: String, newQuantity: Int) {
        if (!canUseCart()) return

        if (newQuantity <= 0) {
            removeFromCart(cartItemId)
            return
        }

        cartRepository.updateItemQuantity(cartItemId, newQuantity)
        _cartState.value = cartRepository.getCart()
    }

    fun clearCart() {
        if (!canUseCart()) return

        cartRepository.clearCart()
        _cartState.value = Cart()
    }

    fun checkout(): Order {
        if (!canUseCart()) {
            // Retornar orden vacía si no puede usar carrito
            return Order(
                id = "INVALID_ORDER",
                items = emptyList(),
                totalAmount = 0.0,
                status = OrderStatus.REJECTED,
                rejectionReason = "Usuario no autorizado"
            )
        }

        val cart = _cartState.value
        val totalAmount = cart.totalAmount

        // Rechazar SOLO si el monto supera los 100,000 CLP
        val status = if (totalAmount > 100000) {
            OrderStatus.REJECTED
        } else {
            OrderStatus.APPROVED
        }

        val order = Order(
            id = "ORDEN N-${System.currentTimeMillis()}",
            items = cart.items,
            totalAmount = totalAmount,
            status = status,
            rejectionReason = if (status == OrderStatus.REJECTED)
                "Monto insuficiente. Pruebe con otro medio de pago"
            else null
        )

        _lastOrder.value = order
        cartRepository.saveLastOrder(order)

        // Si la orden fue aprobada, limpiar el carrito
        if (status == OrderStatus.APPROVED) {
            clearCart()
        }

        return order
    }

    fun getCartItemCount(): Int = if (canUseCart()) _cartState.value.totalItems else 0

    fun getCartTotal(): Double = if (canUseCart()) _cartState.value.totalAmount else 0.0

    // Función para verificar si el usuario actual es admin
    fun isCurrentUserAdmin(): Boolean = currentUser?.isAdmin ?: false
}
