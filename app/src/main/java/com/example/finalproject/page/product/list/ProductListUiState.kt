package com.example.finalproject.page.product.list

import com.example.finalproject.model.Product

data class ProductListUiState (
    val idCategory: String = "",
    val catName: String = "",
    val products: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val searchQuery: String = "",
    val searchSuggestions: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String ?= null
)