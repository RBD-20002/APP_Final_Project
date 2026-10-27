package com.example.finalproject.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.finalproject.model.Category

fun getCategoryIcon(iconkey: String): ImageVector{
    return when(iconkey){
        "Clothing" -> Icons.Default.Checkroom
        "Food" -> Icons.Default.Restaurant
        "Tools" -> Icons.Default.Build
        "Electronics" -> Icons.Default.Devices
        "Health" -> Icons.Default.LocalHospital
        else -> Icons.Default.Category
    }
}

@Composable
fun CategoryGrid(
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit,
    onAddCategoryClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp),
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = categories,
            key = { it.idCategory }
        ) { category ->
            CategoryCard(
                title = category.catName,
                icon = getCategoryIcon(category.catIconKey),
                onClick = { onCategoryClick(category) }
            )
        }
        item {
            AddCard(
                onClick = onAddCategoryClick,
                modifier = Modifier.aspectRatio(1f)
            )
        }
    }
}