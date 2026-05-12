package com.example.recyclerview.data

import com.example.recyclerview.data.model.Product
import com.example.recyclerview.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

interface DataRepository {
    suspend fun getProducts(): Result<List<Product>>
    suspend fun searchProducts(query: String): Result<List<Product>>
    suspend fun getProductById(id: Int): Result<Product>
    suspend fun deleteProduct(id: Int): Result<Unit>
    suspend fun getProductsByCategory(category: String): Result<List<Product>>
}

@Singleton
class DataRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : DataRepository {

    override suspend fun getProducts(): Result<List<Product>> {
        return try {
            Result.success(apiService.getProducts())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchProducts(query: String): Result<List<Product>> {
        return try {
            Result.success(apiService.searchProducts(query))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProductById(id: Int): Result<Product> {
        return try {
            Result.success(apiService.getProductById(id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteProduct(id: Int): Result<Unit> {
        return try {
            apiService.deleteProduct(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProductsByCategory(category: String): Result<List<Product>> {
        return try {
            Result.success(apiService.getProductsByCategory(category))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
