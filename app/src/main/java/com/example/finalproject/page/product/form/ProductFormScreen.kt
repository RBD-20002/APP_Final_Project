package com.example.finalproject.page.product.form

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.finalproject.R
import com.example.finalproject.component.ActionButtons
import com.example.finalproject.component.AttributeTitleDialog
import com.example.finalproject.component.AttributeType
import com.example.finalproject.component.AttributeTypeDialog
import com.example.finalproject.component.CamImageSelector
import com.example.finalproject.component.DynamicAttributeGrid
import com.example.finalproject.component.ProductFieldSection

@Composable
fun ProductFormScreen(
    uiState: ProductFormUiState,
    onEvent: (ProductFormUiEvent) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(uiState.isSuccess) {
        if(uiState.isSuccess){
            onNavigateBack()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            CamImageSelector(
                selectImageUri = uiState.imageUrl?.takeIf { it.isNotBlank() }?.let { Uri.parse(it) },
                defaultCategoryIcon = Icons.Default.Image,
                onImageSelected = { uri ->
                    if (uri != null) {
                        onEvent(ProductFormUiEvent.OnImageChanged(uri.toString()))
                    }
                }
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
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ProductFieldSection(
                        name = uiState.name,
                        onNameChange = { onEvent(ProductFormUiEvent.OnNameChanged(it)) },
                        quantity = uiState.amount,
                        onQuantityChange = { onEvent(ProductFormUiEvent.OnAmountChanged(it)) },
                        price = uiState.price,
                        onPriceChange = { onEvent(ProductFormUiEvent.OnPriceChanged(it)) },
                        location = uiState.marketName,
                        onLocationChange = { onEvent(ProductFormUiEvent.OnMarketNameChanged(it)) }
                    )

                    uiState.dynamicAttributes.forEachIndexed { index, item ->
                        DynamicAttributeGrid(
                            item = item,
                            onKeyChange = {},
                            onValueChange = { onEvent(ProductFormUiEvent.OnUpdateAttributeValue(index, it)) },
                            onDelete = { onEvent(ProductFormUiEvent.OnRemoveAttribute(index))}
                        )
                    }

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(2.dp, Color.Black),
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        IconButton(
                            onClick = {
                                onEvent(ProductFormUiEvent.OnOpenAttributeDialog)
                            },
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "AGREGAR ATRIBUTO",
                                tint = Color.White
                            )
                        }
                    }
                }
            }

            if(uiState.errorMessage != null){
                Text(
                    text = uiState.errorMessage,
                    color = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            ActionButtons(
                cancelText = stringResource(id = R.string.btn_cancel),
                submitText = if (uiState.isEditing) stringResource(id = R.string.btn_save) else stringResource(id = R.string.btn_create),
                onCancel = onNavigateBack,
                onSubmit = { onEvent(ProductFormUiEvent.OnSaveClick) },
                isSubmitEnabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            if(uiState.isLoading){
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.Black
                )
            }
        }

        if (uiState.isAttributeDialogOpen) {
            AttributeTypeDialog(
                onDismissRequest = {
                    onEvent(ProductFormUiEvent.OnDismissAttributeDialog)
                },
                onTypeSelected = { selectedType ->
                    onEvent(ProductFormUiEvent.OnDismissAttributeDialog)
                    if (selectedType == AttributeType.TEXT) {
                        onEvent(ProductFormUiEvent.OnOpenTitleDialog(selectedType))
                    } else {
                        onEvent(
                            ProductFormUiEvent.OnAddAttribute(
                                key = context.getString(selectedType.labelRes),
                                type = selectedType
                            )
                        )
                    }
                },
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (uiState.isTitleDialogOpen && uiState.selectedAttributeTypeForDialog != null) {
            AttributeTitleDialog(
                onDismissRequest = {
                    onEvent(ProductFormUiEvent.OnDismissTitleDialog)
                },
                onConfirmTitle = { typedTitle ->
                    onEvent(
                        ProductFormUiEvent.OnAddAttribute(
                            key = typedTitle,
                            type = uiState.selectedAttributeTypeForDialog!!
                        )
                    )
                },
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}