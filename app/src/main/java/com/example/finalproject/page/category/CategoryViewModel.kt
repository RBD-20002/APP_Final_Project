package com.example.finalproject.page.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.finalproject.data.repository.category.CategoryRepository
import com.example.finalproject.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    fun onEvent(event: CategoryUiEvent) {
        when (event) {
            CategoryUiEvent.OnOpenCreateDialog -> {
                _uiState.update { it.copy(isCreateDialogOpen = true) }
            }
            CategoryUiEvent.OnDismissCreateDialog -> {
                _uiState.update { it.copy(isCreateDialogOpen = false) }
            }
            is CategoryUiEvent.OnCreateCategory -> {
                createCategory(event.name, event.iconKey)
            }
            is CategoryUiEvent.OnSearchQueryChange -> {
                filterCategories(event.query)
            }
            is CategoryUiEvent.OnSearchSubmit -> {
                filterCategories(event.query)
            }
            is CategoryUiEvent.OnSelectBottomNavTab -> {
                _uiState.update { it.copy(selectedBottomBarIndex = event.index) }
            }
            CategoryUiEvent.OnClearError -> {
                _uiState.update { it.copy(errorMessage = null) }
            }
            CategoryUiEvent.OnRefresh -> {
                loadCategories()
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = categoryRepository.getCategories()

            result.onSuccess { remoteCategoriesDTO ->
                val mappedCategories = remoteCategoriesDTO.map { dto ->
                    Category(
                        idCategory = dto.idCategory,
                        catName = dto.catName,
                        catIconKey = dto.catIconKey,
                        idUser = dto.idUser ?: "user_default"
                    )
                }

                _uiState.update {
                    it.copy(
                        categories = mappedCategories,
                        filteredCategories = mappedCategories,
                        searchSuggestions = mappedCategories.map { cat -> cat.catName },
                        isLoading = false
                    )
                }
            }

            result.onFailure { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Error al cargar categorías"
                    )
                }
            }
        }
    }

    private fun filterCategories(query: String) {
        val trimmed = query.trim()
        val currentCategories = _uiState.value.categories
        val filtered = if (trimmed.isBlank()) {
            currentCategories
        } else {
            currentCategories.filter { it.catName.contains(trimmed, ignoreCase = true) }
        }
        val suggestions = currentCategories
            .filter { it.catName.contains(trimmed, ignoreCase = true) }
            .map { it.catName }

        _uiState.update {
            it.copy(
                searchQuery = trimmed,
                filteredCategories = filtered,
                searchSuggestions = suggestions
            )
        }
    }

    private fun createCategory(name: String, iconKey: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isCreateDialogOpen = false) }
            val result = categoryRepository.createCategory(name, iconKey)

            result.onSuccess {
                loadCategories()
            }

            result.onFailure { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Error al crear la categoría"
                    )
                }
            }
        }
    }
}