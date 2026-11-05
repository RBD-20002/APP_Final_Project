package com.example.finalproject.page.product.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.finalproject.component.ProductList
import com.example.finalproject.component.Search
import com.example.finalproject.model.Product
import androidx.compose.ui.unit.sp
import com.example.finalproject.R

@Composable
fun ProductListScreen(
    uiState: ProductListUiState,
    onEvent: (ProductListUiEvent) -> Unit,
    onProductClick: (Product) -> Unit,
    onAddProductClick: (idCategory: String) -> Unit,
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
                onSearch = { query ->
                    onEvent(ProductListUiEvent.OnSearchSubmitted(query))
                },
                searchResults = uiState.searchSuggestions,
                isExpanded = false,
                placeholderText = if (uiState.catName.isNotBlank()) {
                    stringResource(id = R.string.list_search_in_category, uiState.catName)
                } else {
                    stringResource(id = R.string.list_search_all)
                },
                onResultClick = { selectedResult ->
                    onEvent(ProductListUiEvent.OnSearchSubmitted(selectedResult))
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

            ProductList(
                products = uiState.filteredProducts,
                onProductClick = onProductClick,
                onAddProductClick = { onAddProductClick(uiState.idCategory) },
                onIncreaseQuantity = { product -> onEvent(ProductListUiEvent.OnIncreaseQuantity(product)) },
                onDecreaseQuantity = { product -> onEvent(ProductListUiEvent.OnDecreaseQuantity(product)) },
                modifier = Modifier.weight(1f)
            )
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.Black
            )
        }

        if (!uiState.isLoading && uiState.filteredProducts.isEmpty() && uiState.products.isEmpty() && uiState.errorMessage == null) {
            Text(
                text = stringResource(id = R.string.list_empty_state),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}