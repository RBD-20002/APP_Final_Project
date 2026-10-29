package com.example.finalproject.page.product.detail

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproject.R
import com.example.finalproject.component.ButtomDelete
import com.example.finalproject.component.CamImageSelector
import com.example.finalproject.component.CustomInputField
import com.example.finalproject.component.DeleteConfirmDialog
import com.example.finalproject.component.ProductFieldSection
import com.example.finalproject.component.Search
import com.example.finalproject.model.Product

@Composable
fun ProductDetailScreen(
    uiState: ProductDetailUiState,
    onEvent: (ProductDetailUiEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToEdit: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val searchState = rememberTextFieldState()

    LaunchedEffect(uiState.isDeleted) {
        if (uiState.isDeleted) {
            onNavigateBack()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Search(
                textFieldState = searchState,
                onSearch = { /*  Búsqueda o filtrado */ },
                searchResults = emptyList(),
                isExpanded = false,
                placeholderText = stringResource(id = R.string.detail_search_placeholder),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            )

            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                )
            }

            uiState.product?.let { product ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CamImageSelector(
                        selectImageUri = product.imageUrl?.takeIf { it.isNotBlank() }?.let { Uri.parse(it) },
                        defaultCategoryIcon = Icons.Default.Image,
                        onImageSelected = {},
                        isReadOnly = true
                    )

                    Text(
                        text = product.proName.uppercase(),
                        fontWeight = FontWeight.Black,
                        fontSize = 24.sp,
                        color = Color.White
                    )

                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Gray),
                        border = BorderStroke(2.dp, Color.Black),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            ProductFieldSection(
                                name = product.proName,
                                onNameChange = {},
                                quantity = product.proAmount.toString(),
                                onQuantityChange = {},
                                price = product.proPrice.toString(),
                                onPriceChange = {},
                                location = product.proMarketName,
                                onLocationChange = {},
                                readOnly = true
                            )

                            product.dynamicAttributes.forEach { (key, value) ->
                                CustomInputField(
                                    value = value.toString().replace("\"", ""),
                                    onValueChange = {},
                                    placeholder = "",
                                    label = key,
                                    readOnly = true
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    border = BorderStroke(2.dp, Color.Black),
                                    modifier = Modifier.size(54.dp)
                                ) {
                                    IconButton(
                                        onClick = { onNavigateToEdit(product) },
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "EDITAR PRODUCTO",
                                            tint = Color.White
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.size(12.dp))

                                ButtomDelete(
                                    onClick = { onEvent(ProductDetailUiEvent.OnShowDeleteDialog) }
                                )
                            }
                        }
                    }
                }
            } ?: run {
                if (!uiState.isLoading && uiState.errorMessage == null) {
                    Text(
                        text = stringResource(id = R.string.detail_error_not_in_list),
                        modifier = Modifier.padding(top = 40.dp)
                    )
                }
            }
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.Black
            )
        }

        if (uiState.showDeleteDialog) {
            DeleteConfirmDialog(
                onConfirm = { onEvent(ProductDetailUiEvent.OnConfirmDelete) },
                onDismiss = { onEvent(ProductDetailUiEvent.OnDismissDeleteDialog) },
                title = stringResource(id = R.string.dialog_delete_title),
                description = "¿Seguro que deseas eliminar ${uiState.product?.proName ?: "este producto"}?"
            )
        }
    }
}