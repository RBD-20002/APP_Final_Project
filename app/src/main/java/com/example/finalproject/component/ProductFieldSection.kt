package com.example.finalproject.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.finalproject.R

@Composable
fun ProductFieldSection(
    name: String,
    onNameChange: (String) -> Unit,
    quantity: String,
    onQuantityChange: (String) -> Unit,
    price: String,
    onPriceChange: (String) -> Unit,
    location: String,
    onLocationChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CustomInputField(
            value = name,
            onValueChange = onNameChange,
            label = stringResource(id = R.string.form_name_label),
            placeholder = if (readOnly) "" else stringResource(id = R.string.form_name_placeholder),
            readOnly = readOnly
        )

        CustomInputField(
            value = quantity,
            onValueChange = onQuantityChange,
            label = stringResource(id = R.string.form_quantity_label),
            placeholder = if (readOnly) "" else stringResource(id = R.string.form_quantity_placeholder),
            readOnly = readOnly
        )

        CustomInputField(
            value = price,
            onValueChange = onPriceChange,
            label = stringResource(id = R.string.form_price_label),
            placeholder = if (readOnly) "" else stringResource(id = R.string.form_price_placeholder),
            readOnly = readOnly
        )

        LocationField(
            value = location,
            onValueChange = onLocationChange,
            label = stringResource(id = R.string.form_location_label),
            placeholder = if (readOnly) "" else stringResource(id = R.string.form_location_placeholder),
            readOnly = readOnly
        )
    }
}