package com.example.finalproject.page.product.list

import com.example.finalproject.model.Product

sealed interface ProductListUiEvent {
    data class LoadProducts(val idUser: String, val idCategory: String, val catName: String = ""): ProductListUiEvent
    data class OnSearchQueryChanged(val query: String): ProductListUiEvent
    data class OnSearchSubmitted(val query: String): ProductListUiEvent
    data object OnClearSearch: ProductListUiEvent
    data object OnClearError: ProductListUiEvent
    data class OnIncreaseQuantity(val product: Product): ProductListUiEvent
    data class OnDecreaseQuantity(val product: Product): ProductListUiEvent
}