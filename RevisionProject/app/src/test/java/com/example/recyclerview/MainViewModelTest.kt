package com.example.recyclerview

import com.example.recyclerview.data.DataRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
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
        val expectedItems = listOf("Mock Item 1", "Mock Item 2")
        every { repository.getItems() } returns expectedItems
        
        // When
        viewModel = MainViewModel(repository)
        
        // Then
        assertEquals(expectedItems, viewModel.uiState.value)
    }

    @Test
    fun `uiState should be empty if repository returns empty list`() = runTest {
        // Given
        every { repository.getItems() } returns emptyList()
        
        // When
        viewModel = MainViewModel(repository)
        
        // Then
        assert(viewModel.uiState.value.isEmpty())
    }
}
