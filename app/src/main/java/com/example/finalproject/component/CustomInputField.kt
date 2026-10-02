package com.example.finalproject.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    label: String? = null,
    singleLine: Boolean = true
){
    Column(
        modifier = modifier
    ) {
        if(!label.isNullOrBlank()){
            Text(
                text = label.uppercase(),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = Color.Black
            )
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            shape = RoundedCornerShape(12.dp),
            singleLine = singleLine,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.DarkGray,
                focusedBorderColor = Color.Black,
                focusedTextColor = Color.White,
                focusedPlaceholderColor = Color.White
            )
        )
    }
}

/*
@Preview(showBackground = true)
@Composable
fun previewCustomInputField(){
    CustomInputField(
        value = "",
        onValueChange = {},
        label = "LABEL",
        placeholder = "ENTRY NAME ............."
    )
}
*/