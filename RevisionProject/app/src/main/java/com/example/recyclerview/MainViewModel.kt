package com.example.recyclerview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.data.DataRepository
import com.example.recyclerview.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * SENIOR DEV CONCEPT: UI State Modeling
 * Instead of multiple variables, we use a single State object (Sealed Interface).
 * This makes the UI predictable and prevents "illegal states" (e.g., showing loading and data simultaneously).
 */
sealed interface ProductUiState {
    data object Loading : ProductUiState
    data class Success(val products: List<Product>) : ProductUiState
    data class Error(val message: String) : ProductUiState
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    init {
        fetchProducts()
    }

    /**
     * SENIOR DEV CONCEPT: Structured Concurrency
     * We use viewModelScope to launch coroutines. This ensures that if the
     * ViewModel is cleared, the network request is automatically cancelled,
     * preventing memory leaks.
     */
    fun fetchProducts() {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            
            repository.getProducts()
                .onSuccess { products ->
                    _uiState.value = ProductUiState.Success(products)
                }
                .onFailure { error ->
                    _uiState.value = ProductUiState.Error(error.message ?: "Unknown Error")
                }
        }
    }

    /**
     * SENIOR DEV CONCEPT: Unidirectional Data Flow (UDF)
     * Events flow up (UI -> VM) and State flows down (VM -> UI).
     */
    fun onProductClicked(product: Product) {
        // Handle click logic (e.g., analytics, logging)
        println("Clicked on: ${product.name}")
    }
}
