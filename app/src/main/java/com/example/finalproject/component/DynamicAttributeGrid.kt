package com.example.finalproject.component

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

data class DynamicAttributeItem(
    val key: String,
    val value: String,
    val type: AttributeType = AttributeType.TEXT,
)

@Composable
fun DynamicAttributeGrid(
    item: DynamicAttributeItem,
    onKeyChange: (String) -> Unit,
    onValueChange: (String) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = item.key,
            onValueChange = onKeyChange,
            placeholder = { Text(
                text = "ENTER ATTRIBUTE",
                fontWeight = FontWeight.ExtraBold
            ) },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                when (item.type) {
                    AttributeType.TEXT -> {
                        OutlinedTextField(
                            value = item.value,
                            onValueChange = onValueChange,
                            placeholder = { Text("ENTER VALUE") },
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            )
                        )
                    }

                    AttributeType.DATE -> {
                        val calendar = Calendar.getInstance()

                        val dateDialog = DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                onValueChange("$dayOfMonth/${month + 1}/$year")
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        )

                        OutlinedTextField(
                            value = item.value,
                            onValueChange = {},
                            readOnly = true,
                            placeholder = { Text("SELECT DAY") },
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { dateDialog.show() },
                            trailingIcon = {
                                IconButton(
                                    onClick = { dateDialog.show() }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = "DATE",
                                    )
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            )
                        )
                    }

                    AttributeType.STATUS -> {
                        val statusOption = listOf("ACTIVO", "INACTIVO", "PENDIENTE")

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = item.value,
                                onValueChange = {},
                                readOnly = true,
                                placeholder = { Text("SELECT STATUS") },
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        isDropdownExpanded = true
                                    },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White
                                )
                            )

                            DropdownMenu(
                                expanded = isDropdownExpanded,
                                onDismissRequest = { isDropdownExpanded = false }
                            ) {
                                statusOption.forEach { option ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(option, fontWeight = FontWeight.ExtraBold)
                                        },
                                        onClick = {
                                            onValueChange(option)
                                            isDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            ButtomDelete(
                onClick = onDelete
            )
        }
    }
}

/*
@Preview
@Composable
fun previewDynamicAttributeGrid() {
    var textItem by remember {
        mutableStateOf(
            DynamicAttributeItem(
                key = "TALLA",
                value = "XL",
                type = AttributeType.TEXT
            )
        )
    }

    var dateItem by remember {
        mutableStateOf(
            DynamicAttributeItem(
                key = "FECHA COMPRA",
                value = "15/10/2026",
                type = AttributeType.DATE
            )
        )
    }

    var statusItem by remember {
        mutableStateOf(
            DynamicAttributeItem(
                key = "DISPONIBILIDAD",
                value = "ACTIVO",
                type = AttributeType.STATUS
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DynamicAttributeGrid(
            item = textItem,
            onKeyChange = { textItem = textItem.copy(key = it) },
            onValueChange = { textItem = textItem.copy(value = it) },
            onDelete = {}
        )

        DynamicAttributeGrid(
            item = dateItem,
            onKeyChange = { dateItem = dateItem.copy(key = it) },
            onValueChange = { dateItem = dateItem.copy(value = it) },
            onDelete = {}
        )

        DynamicAttributeGrid(
            item = statusItem,
            onKeyChange = { statusItem = statusItem.copy(key = it) },
            onValueChange = { statusItem = statusItem.copy(value = it) },
            onDelete = {}
        )
    }
}
*/