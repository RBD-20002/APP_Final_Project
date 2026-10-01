package com.example.finalproject.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String = "",
)

@Composable
fun BottomBar(
    items: List<BottomNavItem>,
    selectIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectIndex == index,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        imageVector = item.icon, contentDescription = item.title
                    )
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun BottomPreview() {
//    var listItems = listOf(
//        BottomNavItem(title = "HOME", icon = Icons.Default.Home),
//        BottomNavItem(title = "FAVORITE", icon = Icons.Default.Favorite),
//        BottomNavItem(title = "PROFILE", icon = Icons.Default.AccountCircle),
//        BottomNavItem(title = "SETTIGNS", icon = Icons.Default.Settings)
//    )
//    BottomBar(
//        items = listItems,
//        selectIndex = 0,
//        onItemSelected = {}
//    )
//}