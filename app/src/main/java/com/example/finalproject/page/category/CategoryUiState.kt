package com.example.finalproject.page.category

import com.example.finalproject.model.Category

data class CategoryUiState (
    val categories: List<Category> = emptyList(),
    val filteredCategories: List<Category> = emptyList(),
    val searchQuery: String = "",
    val searchSuggestions: List<String> = emptyList(),
    val selectedBottomBarIndex: Int = 0,
    val isCreateDialogOpen: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)