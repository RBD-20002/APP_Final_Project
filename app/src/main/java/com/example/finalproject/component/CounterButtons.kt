package com.example.finalproject.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CounterButtons(
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    onDecreaseText: String = "-",
    onIncreaseText: String = "+",
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .width(85.dp)
            .height(42.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        OutlinedButton(
            onClick = onIncrease,
            modifier = Modifier.width(40.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(Color.White),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = onIncreaseText,
                color = Color.Black,
                fontSize = 30.sp
            )
        }
        Button(
            onClick = onDecrease,
            modifier = Modifier.width(40.dp),
            colors = ButtonDefaults.buttonColors(Color.White),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = onDecreaseText,
                color = Color.Black,
                fontSize = 34.sp
            )
        }
    }
}