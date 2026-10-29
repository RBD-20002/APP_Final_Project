package com.example.finalproject.page.product.form

import com.example.finalproject.component.AttributeType
import com.example.finalproject.model.DynamicAttributeItem

data class ProductFormUiState (
    val productId: String ?= null,
    val idCategory: String = "",
    val idUser: String = "",
    val name: String = "",
    val amount: String = "",
    val price: String = "",
    val marketName: String = "",
    val status: String ?= null,
    val imageUrl: String ?= null,
    val dynamicAttributes: List<DynamicAttributeItem> = emptyList(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String ?= null,
    val isAttributeDialogOpen: Boolean = false,
    val isTitleDialogOpen: Boolean = false,
    val selectedAttributeTypeForDialog: AttributeType? = null
) {
    val isEditing: Boolean
        get() = productId != null

    companion object {
        val STATUS_OPTIONS = listOf("GUARDADO","PRESTADA","NINGUNA")
    }
}