package com.example.finalproject.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.finalproject.R

@Composable
fun AttributeTitleDialog(
    onDismissRequest: () -> Unit,
    onConfirmTitle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var attributeTitle by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(2.dp, Color.Black),
            color = Color.White,
            modifier = modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.attr_dialog_new_title),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = Color.Black
                )

                Text(
                    text = stringResource(id = R.string.attr_dialog_new_desc),
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )

                OutlinedTextField(
                    value = attributeTitle,
                    onValueChange = { attributeTitle = it },
                    placeholder = {
                        Text(
                            stringResource(
                                id = R.string.attr_dialog_placeholder_ej
                            ),
                            color = Color.LightGray
                        ) },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Icono lápiz",
                            tint = Color.Black
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onDismissRequest,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            stringResource(R.string.btn_cancel),
                            color = Color.Black)
                    }

                    Button(
                        onClick = {
                            if (attributeTitle.isNotBlank()) {
                                onConfirmTitle(attributeTitle.trim())
                                onDismissRequest()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            stringResource(R.string.btn_create),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
