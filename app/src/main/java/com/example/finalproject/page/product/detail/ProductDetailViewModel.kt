package com.example.finalproject.page.product.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.finalproject.data.repository.product.ProductRepository
import com.example.finalproject.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun onEvent(event: ProductDetailUiEvent){
        when(event){
            is ProductDetailUiEvent.LoadProduct -> {
                loadProduct(event.idProduct, event.idUser)
            }
            is ProductDetailUiEvent.SetProduct -> {
                _uiState.update { it.copy(product = event.product, isLoading = false) }
            }
            ProductDetailUiEvent.OnShowDeleteDialog -> {
                _uiState.update { it.copy(showDeleteDialog = true) }
            }
            ProductDetailUiEvent.OnDismissDeleteDialog -> {
                _uiState.update { it.copy(showDeleteDialog = false) }
            }
            ProductDetailUiEvent.OnConfirmDelete -> {
                deleteProduct()
            }
            ProductDetailUiEvent.OnClearError -> {
                _uiState.update { it.copy(errorMessage = null) }
            }
        }
    }

    private fun loadProduct(idProduct: String, idUser: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = productRepository.getProductByUser()

            result.onSuccess { productListDTO ->
                val targetProductDTO = productListDTO.find { it.idProduct == idProduct }

                if (targetProductDTO != null) {
                    val mappedProduct = Product(
                        idProduct = targetProductDTO.idProduct,
                        proName = targetProductDTO.proName,
                        proAmount = targetProductDTO.proAmount,
                        proPrice = targetProductDTO.proPrice,
                        proMarketName = targetProductDTO.proMarketName,
                        proStatus = targetProductDTO.proStatus,
                        imageUrl = targetProductDTO.imageUrl,
                        idCategory = targetProductDTO.idCategory,
                        idUser = targetProductDTO.idUser,
                        dynamicAttributes = targetProductDTO.dynamicAttributes
                    )

                    _uiState.update { it.copy(isLoading = false, product = mappedProduct) }
                } else {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = "El producto no se encontró en la lista.")
                    }
                }
            }

            result.onFailure { e ->
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.localizedMessage ?: "ERROR AL CARGAR PRODUCTO")
                }
            }
        }
    }

    private fun deleteProduct() {
        val currentProduct = _uiState.value.product
        if (currentProduct == null) {
            _uiState.update { it.copy(errorMessage = "No hay producto para eliminar") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, showDeleteDialog = false) }
            val result = productRepository.deleteProduct(currentProduct.idProduct)

            result.onSuccess {
                _uiState.update { it.copy(isLoading = false, isDeleted = true) }
            }
            result.onFailure { e ->
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.localizedMessage ?: "ERROR AL ELIMINAR PRODUCTO")
                }
            }
        }
    }
}