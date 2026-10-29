package com.example.finalproject.page.product.detail

import com.example.finalproject.model.Product

sealed interface ProductDetailUiEvent {
    data class LoadProduct(val idProduct: String, val idUser: String): ProductDetailUiEvent
    data class SetProduct(val product: Product): ProductDetailUiEvent
    data object OnShowDeleteDialog: ProductDetailUiEvent
    data object OnDismissDeleteDialog: ProductDetailUiEvent
    data object OnConfirmDelete: ProductDetailUiEvent
    data object OnClearError: ProductDetailUiEvent
}