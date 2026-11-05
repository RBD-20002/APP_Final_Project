package com.example.finalproject.page.product.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.remote.dto.product.ProductUpdateDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.finalproject.data.repository.product.ProductRepository
import com.example.finalproject.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.json.JsonPrimitive
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    fun onEvent(event: ProductListUiEvent){
        when (event) {
            is ProductListUiEvent.LoadProducts -> {
                loadProductsByCategory(event.idUser, event.idCategory, event.catName)
            }
            is ProductListUiEvent.OnSearchQueryChanged -> {
                updateSearchQuery(event.query)
            }
            is ProductListUiEvent.OnSearchSubmitted -> {
                executeSearch(event.query)
            }
            ProductListUiEvent.OnClearSearch -> {
                _uiState.update {
                    it.copy( searchQuery = "", filteredProducts = it.products, searchSuggestions = emptyList() )
                }
            }
            ProductListUiEvent.OnClearError -> {
                _uiState.update { it.copy(errorMessage = null) }
            }
            is ProductListUiEvent.OnIncreaseQuantity -> {
                updateProductQuantity(event.product, event.product.proAmount + 1)
            }
            is ProductListUiEvent.OnDecreaseQuantity -> {
                if (event.product.proAmount > 0) {
                    updateProductQuantity(event.product, event.product.proAmount - 1)
                }
            }
        }
    }

    private fun loadProductsByCategory (idUser: String, idCategory: String, catName: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy( isLoading = true, errorMessage = null, idCategory = idCategory, catName = catName)
            }

            val result = productRepository.getProductByUser()

            result.onSuccess { productListDTO ->
                val categoryProductsDTO = productListDTO.filter { it.idCategory == idCategory }
                val mappedProducts = categoryProductsDTO.map { dto ->
                    Product(
                        idProduct = dto.idProduct,
                        proName = dto.proName,
                        proAmount = dto.proAmount,
                        proPrice = dto.proPrice,
                        proMarketName = dto.proMarketName,
                        proStatus = dto.proStatus,
                        imageUrl = dto.imageUrl,
                        idUser = dto.idUser,
                        dynamicAttributes = dto.dynamicAttributes,
                        idCategory = dto.idCategory
                    )
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        products = mappedProducts,
                        filteredProducts = mappedProducts
                    )
                }
            }

            result.onFailure { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "ERROR AL CARGAR LOS PRODUCTOS DE LA CATEGORÍA"
                    )
                }
            }
        }
    }

    private fun updateSearchQuery(query: String) {
        _uiState.update { currentState ->
            val filtered = if (query.isBlank()) {
                currentState.products
            } else {
                currentState.products.filter { product ->
                    product.proName.contains(query, ignoreCase = true) ||
                            product.proMarketName.contains(query, ignoreCase = true) ||
                            product.dynamicAttributes.values.any { it.toString().contains(query, ignoreCase = true) }
                }
            }
            val suggestions = if (query.isBlank()) {
                emptyList()
            } else {
                currentState.products
                    .map { it.proName }
                    .filter { it.contains(query, ignoreCase = true) }
                    .distinct()
                    .take(5)
            }

            currentState.copy(
                searchQuery = query,
                filteredProducts = filtered,
                searchSuggestions = suggestions
            )
        }
    }

    private fun executeSearch(query: String) {
        updateSearchQuery(query)
    }

    private fun updateProductQuantity(product: Product, newAmount: Int){
        if (newAmount < 0 || product.proAmount == newAmount) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val updateDTO = ProductUpdateDTO(
                idProduct = product.idProduct,
                proName = product.proName,
                proAmount = newAmount,
                proPrice = product.proPrice,
                proMarketName = product.proMarketName,
                proStatus = product.proStatus ?: "NONE",
                imageUrl = product.imageUrl ?: "",
                idCategory = product.idCategory,
                idUser = product.idUser,
                dynamicAttributes = product.dynamicAttributes.entries.associate {
                    it.key to JsonPrimitive(it.value.toString())
                }
            )
            val result = productRepository.updateProduct(updateDTO)

            result.onSuccess {
                _uiState.update { currentState ->
                    val updatedProducts = currentState.products.map {
                        if (it.idProduct == product.idProduct) it.copy(proAmount = newAmount) else it
                    }
                    val updatedFiltered = currentState.filteredProducts.map {
                        if (it.idProduct == product.idProduct) it.copy(proAmount = newAmount) else it
                    }
                    currentState.copy(
                        isLoading = false,
                        products = updatedProducts,
                        filteredProducts = updatedFiltered
                    )
                }
            }

            result.onFailure { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Error al actualizar la cantidad"
                    )
                }
            }
        }
    }
}
