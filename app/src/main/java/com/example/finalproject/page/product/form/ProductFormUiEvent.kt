package com.example.finalproject.page.product.form

import com.example.finalproject.component.AttributeType

sealed interface ProductFormUiEvent {
    data class InitForm(val idCategory: String, val idUser: String, val idProduct: String? = null) : ProductFormUiEvent
    data class OnNameChanged(val name: String) : ProductFormUiEvent
    data class OnAmountChanged(val amount: String) : ProductFormUiEvent
    data class OnPriceChanged(val price: String) : ProductFormUiEvent
    data class OnMarketNameChanged(val marketName: String) : ProductFormUiEvent
    data class OnStatusChanged(val status: String?) : ProductFormUiEvent
    data class OnAddAttribute(val key: String, val type: AttributeType) : ProductFormUiEvent
    data class OnUpdateAttributeValue(val index: Int, val value: String) : ProductFormUiEvent
    data class OnRemoveAttribute(val index: Int) : ProductFormUiEvent
    data object OnSaveClick : ProductFormUiEvent
    data object OnClearError : ProductFormUiEvent
    data object OnOpenAttributeDialog : ProductFormUiEvent
    data object OnDismissAttributeDialog : ProductFormUiEvent
    data class OnOpenTitleDialog(val type: AttributeType) : ProductFormUiEvent
    data object OnDismissTitleDialog : ProductFormUiEvent
    data class OnImageChanged(val imageUrl: String) : ProductFormUiEvent
}