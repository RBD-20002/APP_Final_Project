package com.example.finalproject.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CustomInputField(
            value = name,
            onValueChange = onNameChange,
            label = "NAME",
            placeholder = "ENTER NAME.............."
        )

        CustomInputField(
            value = quantity,
            onValueChange = onQuantityChange,
            label = "QUANTITY",
            placeholder = "ENTER QUANTITY................."
        )

        CustomInputField(
            value = price,
            onValueChange = onPriceChange,
            label = "PRICE",
            placeholder = "ENTER PRICE......................."
        )

        LocationField(
            value = location,
            onValueChange = onLocationChange,
            label = "STORE LOCATION",
            placeholder = "ENTER STORE LOCATION................"
        )
    }
}

/*
@Preview(showBackground = true)
@Composable
fun previewProductFieldSection(){
    ProductFieldSection(
        name = "",
        onNameChange = {},
        quantity = "",
        onQuantityChange = {},
        price = "",
        onPriceChange = {},
        location = "",
        onLocationChange = {}
    )
}
*/