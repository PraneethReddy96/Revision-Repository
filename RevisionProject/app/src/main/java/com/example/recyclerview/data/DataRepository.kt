package com.example.recyclerview.data

import com.example.recyclerview.data.model.Product
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

interface DataRepository {
    suspend fun getProducts(): Result<List<Product>>

    // 1. MY ADDITION: Search functionality
    suspend fun searchProducts(query: String): Result<List<Product>>

    // YOUR TURN - Completed function signatures
    suspend fun getProductById(id: Int): Result<Product>

    suspend fun deleteProduct(product: Product): Result<Unit>

    suspend fun getProductsByCategory(category: String): Result<List<Product>>
}

@Singleton
class DataRepositoryImpl @Inject constructor() : DataRepository {

    // Mock local data source
    private val mockProducts = mutableListOf(
        Product(1, "Smartphone", "High-end flagship device", 999.99, "Electronics"),
        Product(2, "Laptop", "Powerful workstation for pros", 1499.00, "Electronics"),
        Product(3, "Coffee Maker", "Brews the perfect espresso", 79.50, "Home"),
        Product(4, "Running Shoes", "Lightweight and durable", 120.00, "Sports"),
        Product(5, "Desk Lamp", "Adjustable LED lighting", 45.00, "Home")
    )

    override suspend fun getProducts(): Result<List<Product>> {
        return try {
            delay(500)
            Result.success(mockProducts.toList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchProducts(query: String): Result<List<Product>> {
        return try {
            delay(300)
            val filtered = mockProducts.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
            Result.success(filtered)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // YOUR TURN - Implementation
    
    override suspend fun getProductById(id: Int): Result<Product> {
        return try {
            delay(200)
            val product = mockProducts.find { it.id == id }
            if (product != null) {
                Result.success(product)
            } else {
                Result.failure(Exception("Product with ID $id not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteProduct(product: Product): Result<Unit> {
        return try {
            delay(400)
            val removed = mockProducts.remove(product)
            if (removed) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Product could not be deleted"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProductsByCategory(category: String): Result<List<Product>> {
        return try {
            delay(300)
            val filtered = mockProducts.filter { it.category.equals(category, ignoreCase = true) }
            Result.success(filtered)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
