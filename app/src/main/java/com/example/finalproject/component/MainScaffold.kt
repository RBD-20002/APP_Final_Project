package com.example.finalproject.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit
){
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = topBar,
        bottomBar = bottomBar,
        content = content
    )
}
/*
@Preview(
    name = "MAIN SCAFOLD",
    showSystemUi = true
)
@Composable
fun PreviewMainScaffold(){
    val searchState = rememberTextFieldState()
    var selectedIndex by remember { mutableIntStateOf(0) }

    val navItems = listOf(
        BottomNavItem(title = "INICIO", icon = Icons.Default.Home),
        BottomNavItem(title = "PERFIL", icon = Icons.Default.Person)
    )

    MainScaffold(
        topBar = {
            Search(
                textFieldState = searchState,
                onSearch = {},
                searchResults = listOf("MARTILLO", "TALADRO")
            )
        },

        bottomBar = {
            BottomBar(
                items = navItems,
                selectIndex = selectedIndex,
                onItemSelected = { selectedIndex = it }
            )
        }
    ) {

    }
}
*/