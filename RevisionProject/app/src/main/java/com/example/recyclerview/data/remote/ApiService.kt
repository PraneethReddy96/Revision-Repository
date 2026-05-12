package com.example.recyclerview.data.remote

import com.example.recyclerview.data.model.Product
import retrofit2.http.*

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product

    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String): List<Product>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): List<Product>

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int)
}
