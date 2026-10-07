package com.example.finalproject.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StatusDropdownSelector(
    selectedStatus: String,
    onStatusSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "STATUS",
    options: List<String> = listOf("SAVED", "BORROWED")
){
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = label.uppercase(),
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )

        Spacer(Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ){
            OutlinedTextField(
                value = selectedStatus.uppercase(),
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("SELECTED") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "DISPLAY STATES",
                        tint = Color.White
                    )
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{ expanded = true},
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.DarkGray,
                    unfocusedContainerColor = Color.DarkGray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option.uppercase(),
                                fontWeight = FontWeight.ExtraBold
                            )
                        },
                        onClick = {
                            onStatusSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun previewStatusDropdownSelector(){
    var currentStatus by remember { mutableStateOf("GUARDADA") }

    StatusDropdownSelector(
        selectedStatus = currentStatus,
        onStatusSelected = { currentStatus = it }
    )
}