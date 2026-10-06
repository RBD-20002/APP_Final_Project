package com.example.finalproject.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ButtomDelete(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, Color.Black),
        modifier = modifier
            .size(54.dp)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(54.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "DELETE ATTRIBUTE"
            )
        }
    }
}

@Preview
@Composable
fun previewButtomDelete(){
    ButtomDelete(
        onClick = {}
    )
}