package com.example.finalproject.page.product.detail

import com.example.finalproject.model.Product

data class ProductDetailUiState (
    val product: Product ?= null,
    val isLoading: Boolean = false,
    val isDeleted: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val errorMessage: String ?= null
)