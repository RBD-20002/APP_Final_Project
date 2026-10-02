package com.example.finalproject.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class ProductUiModel(
    val id: String,
    val name: String,
    val quantity: Int,
    val attributes: Map<String, String>
)

@Composable
fun ProductList(
    products: List<ProductUiModel>,
    onProductClick: (ProductUiModel) -> Unit,
    onAddProductClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            items = products,
            key = { product ->  product.id }
        ){
            product ->
            ProductCardItem(
                title = product.name,
                quantity = product.quantity,
                attributes = product.attributes,
                onClick = { onProductClick(product) }
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

/*
@Preview
@Composable
fun previewProductList(){
    val sampleProductList = listOf(
        ProductUiModel(id = "1", name = "MARTILLO", quantity = 16, attributes = mapOf("CATEGORY" to "TOOLS", "PRICE" to "15.60€", "MATERIAL" to "ACERO")),
        ProductUiModel(id = "2", name = "MARTILLO", quantity = 16, attributes = mapOf("CATEGORY" to "TOOLS", "PRICE" to "15.60€", "MATERIAL" to "ACERO")),
        ProductUiModel(id = "3", name = "MARTILLO", quantity = 16, attributes = mapOf("CATEGORY" to "TOOLS", "PRICE" to "15.60€", "MATERIAL" to "ACERO")),
        ProductUiModel(id = "4", name = "MARTILLO", quantity = 16, attributes = mapOf("CATEGORY" to "TOOLS", "PRICE" to "15.60€", "MATERIAL" to "ACERO")),
        ProductUiModel(id = "5", name = "MARTILLO", quantity = 16, attributes = mapOf("CATEGORY" to "TOOLS", "PRICE" to "15.60€", "MATERIAL" to "ACERO"))
    )

    ProductList(
        products = sampleProductList,
        onAddProductClick = {},
        onProductClick = {}
    )
}
*/