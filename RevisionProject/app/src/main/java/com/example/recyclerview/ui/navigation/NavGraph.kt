package com.example.recyclerview.ui.navigation

import kotlinx.serialization.Serializable

/**
 * SENIOR DEV CONCEPT: Type-Safe Navigation (2026 Standard)
 * We no longer use String routes like "home" or "details/{id}".
 * Instead, we use @Serializable objects/classes which are checked at compile-time.
 */
sealed interface Screen {
    @Serializable
    object ProductList : Screen

    @Serializable
    data class ProductDetail(
        val id: Int,
        val name: String,
        val description: String,
        val price: Double,
        val category: String
    ) : Screen
}
