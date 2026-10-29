package com.example.finalproject.page.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.finalproject.component.CategoryGrid
import com.example.finalproject.component.CreateCategoryDialog
import com.example.finalproject.component.Search
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.finalproject.R

@Composable
fun CategoryScreen(
    uiState: CategoryUiState,
    onEvent: (CategoryUiEvent) -> Unit,
    onNavigateToCategoryProducts: (categoryId: String, categoryName: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val searchState = rememberTextFieldState(initialText = uiState.searchQuery)

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Search(
                textFieldState = searchState,
                placeholderText = stringResource(id = R.string.category_search_placeholder),
                searchResults = uiState.searchSuggestions,
                onSearch = { query ->
                    onEvent(CategoryUiEvent.OnSearchSubmit(query))
                },
                onResultClick = { suggestion ->
                    onEvent(CategoryUiEvent.OnSearchSubmit(suggestion))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }

            CategoryGrid(
                categories = uiState.filteredCategories,
                onCategoryClick = { category ->
                    onNavigateToCategoryProducts(category.idCategory, category.catName)
                },
                onAddCategoryClick = {
                    onEvent(CategoryUiEvent.OnOpenCreateDialog)
                },
                modifier = Modifier.weight(1f)
            )
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.Black
            )
        }

        if (uiState.isCreateDialogOpen) {
            CreateCategoryDialog(
                onDismissRequest = { onEvent(CategoryUiEvent.OnDismissCreateDialog) },
                onConfirmation = { name, iconKey ->
                    onEvent(CategoryUiEvent.OnCreateCategory(name, iconKey))
                }
            )
        }
    }
}