package com.example.recyclerview

import com.example.recyclerview.data.DataRepository
import com.example.recyclerview.data.model.Product
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val repository: DataRepository = mockk()
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadData should populate uiState from repository`() = runTest {
        // Given
        val mockProducts = listOf(
            Product(1, "Smartphone", "High-end flagship device", 999.99, "Electronics")
        )
        // Corrected: use coEvery for suspend functions and return Result.success
        coEvery { repository.getProducts() } returns Result.success(mockProducts)
        
        // When
        viewModel = MainViewModel(repository)
        advanceUntilIdle() // Wait for init block/fetchProducts coroutine to complete
        
        // Then
        val state = viewModel.uiState.value
        assertTrue("State should be Success", state is ProductUiState.Success)
        assertEquals(mockProducts, (state as ProductUiState.Success).products)
    }

    @Test
    fun `uiState should be empty if repository returns empty list`() = runTest {
        // Given
        coEvery { repository.getProducts() } returns Result.success(emptyList())
        
        // When
        viewModel = MainViewModel(repository)
        advanceUntilIdle()
        
        // Then
        val state = viewModel.uiState.value
        assertTrue("State should be Success", state is ProductUiState.Success)
        assertTrue("Products list should be empty", (state as ProductUiState.Success).products.isEmpty())
    }

    @Test
    fun `uiState should be Error if repository returns failure`() = runTest {
        // Given
        val errorMessage = "Network Error"
        coEvery { repository.getProducts() } returns Result.failure(Exception(errorMessage))
        
        // When
        viewModel = MainViewModel(repository)
        advanceUntilIdle()
        
        // Then
        val state = viewModel.uiState.value
        assertTrue("State should be Error", state is ProductUiState.Error)
        assertEquals(errorMessage, (state as ProductUiState.Error).message)
    }
}
