package com.example.finalproject.component

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproject.R
import com.example.finalproject.model.DynamicAttributeItem
import java.util.Calendar

@Composable
fun DynamicAttributeGrid(
    item: DynamicAttributeItem,
    onKeyChange: (String) -> Unit,
    onValueChange: (String) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = item.key.uppercase(),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            color = Color.White
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
                            placeholder = {
                                stringResource(id = R.string.attr_dialog_new_desc)
                            },
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.DarkGray,
                                unfocusedContainerColor = Color.DarkGray,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedPlaceholderColor = Color.LightGray,
                                unfocusedPlaceholderColor = Color.LightGray
                            )
                        )
                    }

                    AttributeType.DATE -> {
                        val calendar = Calendar.getInstance()
                        val dateDialog = remember {
                            DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    val formattedDate = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
                                    onValueChange(formattedDate)
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            )
                        }

                        OutlinedTextField(
                            value = item.value.ifBlank { "" },
                            onValueChange = {},
                            readOnly = true,
                            placeholder = {
                                stringResource(id = R.string.form_attr_date_placeholder)
                            },
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                IconButton(onClick = { dateDialog.show() }) {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = "FECHA",
                                        tint = Color.White
                                    )
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.DarkGray,
                                unfocusedContainerColor = Color.DarkGray,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedPlaceholderColor = Color.LightGray,
                                unfocusedPlaceholderColor = Color.LightGray
                            )
                        )
                    }

                    AttributeType.STATUS -> {
                        StatusDropdownSelector(
                            selectedStatus = item.value.ifBlank { stringResource(id = R.string.status_none) },
                            onStatusSelected = { newStatus ->
                                onValueChange(newStatus)
                            },
                            label = "",
                            options = listOf(
                                stringResource(id = R.string.status_none),
                                stringResource(id = R.string.status_saved),
                                stringResource(id = R.string.status_borrowed)
                            )
                        )
                    }
                }
            }
            ButtomDelete(
                onClick = onDelete
            )
        }
    }
}