package com.example.finalproject.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LocationField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    readOnly: Boolean = true
){
    val context = LocalContext.current

    Box(
        modifier = Modifier.padding(2.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            CustomInputField(
                value = value,
                onValueChange = onValueChange,
                label = label,
                placeholder = placeholder,
                readOnly = readOnly,
                modifier = Modifier
                    .weight(0.8f)
                    .padding(end = 4.dp)
            )
            Card(
                modifier = Modifier
                    .weight(0.2f)
                    .height(56.dp)
                    .clickable {},      //QUEDA PENDIENTE CONECTARLO CON GOOGLE MAPS
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(5.dp, Color.Black),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = "ICON FOR LOCATION",
                        tint = Color.Black,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun previewLocationField(){
    LocationField(
        value = "",
        onValueChange = {},
        label = "PLACE OF PURCHASE",
        placeholder = "ENTER STORE .............."
    )
}
