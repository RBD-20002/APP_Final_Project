package com.example.finalproject.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.finalproject.model.Product

@Composable
fun ProductList(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onAddProductClick: () -> Unit,
    onIncreaseQuantity: (Product) -> Unit,
    onDecreaseQuantity: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            items = products,
            key = { product ->  product.idProduct }
        ){
            product ->
            ProductCardItem(
                title = product.proName,
                quantity = product.proAmount,
                location = product.proMarketName,
                price = product.proPrice,
                status = product.proStatus,
                imageUrl = product.imageUrl,
                attributes = product.dynamicAttributes.mapValues { it.value.toString().replace("\"", "") },
                onClick = { onProductClick(product) },
                onIncrease = { onIncreaseQuantity(product) },
                onDecrease = { onDecreaseQuantity(product) }
            )
        }

        item {
           AddCard(
               onClick = onAddProductClick,
               modifier = Modifier
                   .fillMaxWidth()
                   .height(100.dp)
           )
        }
    }
}