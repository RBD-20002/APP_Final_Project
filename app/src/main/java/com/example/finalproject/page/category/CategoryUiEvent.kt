package com.example.finalproject.page.category

sealed interface CategoryUiEvent {
    data object OnOpenCreateDialog : CategoryUiEvent
    data object OnDismissCreateDialog : CategoryUiEvent
    data class OnCreateCategory(val name: String, val iconKey: String) : CategoryUiEvent
    data class OnSearchQueryChange(val query: String) : CategoryUiEvent
    data class OnSearchSubmit(val query: String) : CategoryUiEvent
    data class OnSelectBottomNavTab(val index: Int) : CategoryUiEvent
    data object OnClearError : CategoryUiEvent
    data object OnRefresh : CategoryUiEvent
}