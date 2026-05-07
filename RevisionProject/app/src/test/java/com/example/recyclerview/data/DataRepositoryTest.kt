package com.example.recyclerview.data

import com.example.recyclerview.data.model.Product
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DataRepositoryTest {

    private lateinit var repository: DataRepositoryImpl

    @Before
    fun setup() {
        repository = DataRepositoryImpl()
    }

    @Test
    fun `getProducts returns success with list of products`() = runTest {
        val result = repository.getProducts()
        assertTrue(result.isSuccess)
        assertEquals(5, result.getOrNull()?.size)
    }

    @Test
    fun `getProductById with existing ID returns product`() = runTest {
        val result = repository.getProductById(1)
        assertTrue(result.isSuccess)
        assertEquals("Smartphone", result.getOrNull()?.name)
    }

    @Test
    fun `getProductById with non-existing ID returns failure`() = runTest {
        val result = repository.getProductById(99)
        assertTrue(result.isFailure)
        assertEquals("Product with ID 99 not found", result.exceptionOrNull()?.message)
    }

    @Test
    fun `searchProducts with partial match returns filtered list`() = runTest {
        val result = repository.searchProducts("lap")
        assertTrue(result.isSuccess)
        val filtered = result.getOrThrow()
        assertEquals(2, filtered.size)
    }

    @Test
    fun `deleteProduct removes item from repository`() = runTest {
        val productToDelete = repository.getProducts().getOrThrow().first()
        val deleteResult = repository.deleteProduct(productToDelete)
        val finalProducts = repository.getProducts().getOrThrow()

        assertTrue(deleteResult.isSuccess)
        assertEquals(4, finalProducts.size)
        assertFalse(finalProducts.contains(productToDelete))
    }

    // --- YOUR TURN SOLUTIONS ---

    @Test
    fun `getProductsByCategory with existing category returns filtered list`() = runTest {
        // When
        val result = repository.getProductsByCategory("Home")

        // Then
        assertTrue(result.isSuccess)
        val products = result.getOrThrow()
        assertEquals(2, products.size)
        assertTrue(products.all { it.category == "Home" })
    }

    @Test
    fun `getProductsByCategory with non-existing category returns empty list`() = runTest {
        // When
        val result = repository.getProductsByCategory("Groceries")

        // Then
        assertTrue(result.isSuccess) // It's still a success, just with 0 items
        val products = result.getOrThrow()
        assertTrue(products.isEmpty())
    }
}
