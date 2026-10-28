package com.example.finalproject.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import com.example.finalproject.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    textFieldState: TextFieldState,
    onSearch: (String) -> Unit,
    searchResults: List<String>,
    modifier: Modifier = Modifier,
    placeholderText: String = stringResource(R.string.list_search_in_category, textFieldState.text),
    isExpanded: Boolean = false,
    onExpandedChange: ((Boolean) -> Unit) ?= null,
    onResultClick: ((String) -> Unit)? = null
){
    var expanded by rememberSaveable { mutableStateOf(isExpanded) }
    val currendExpanded = onExpandedChange?.let { isExpanded } ?: expanded
    val handleExpandedChange: (Boolean) -> Unit = { newExpanded ->
        expanded = newExpanded
        onExpandedChange?.invoke(newExpanded)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .semantics { isTraversalGroup = true },
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    query = textFieldState.text.toString(),
                    onQueryChange = {
                        textFieldState.edit { replace(0,length,it) }
                    },
                    onSearch = {
                        onSearch(textFieldState.text.toString())
                        handleExpandedChange(false)
                    },
                    expanded = currendExpanded,
                    onExpandedChange = handleExpandedChange,
                    placeholder = { Text(placeholderText) },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                onSearch(textFieldState.text.toString())
                                handleExpandedChange(false)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "BUSCAR"
                            )
                        }
                    }
                )
            },
            expanded = currendExpanded,
            onExpandedChange = handleExpandedChange,
            windowInsets = WindowInsets(0.dp)
        ){
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                searchResults.forEach { result ->
                    ListItem(
                        headlineContent = { Text(result) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                textFieldState.edit { replace(0, length, result) }
                                onResultClick?.invoke(result)
                                handleExpandedChange(false)
                            }
                    )
                }
            }
        }
    }
}