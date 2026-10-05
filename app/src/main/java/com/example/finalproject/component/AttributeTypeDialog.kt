package com.example.finalproject.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChecklistRtl
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

enum class AttributeType(val label: String, val icon: ImageVector){
    TEXT("TEXT", Icons.Default.TextFields),
    DATE("DATE", Icons.Default.DateRange),
    STATUS("STATUS", Icons.Default.ChecklistRtl)
}

@Composable
fun AttributeTypeDialog(
    onDismissRequest: () -> Unit,
    onTypeSelected: (AttributeType) -> Unit,
    modifier: Modifier = Modifier
){
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(2.dp,Color.Black),
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
                    text = "ADD ATTRIBUTE",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp
                )

                Text(
                    text = "SELECT THE TYPE OF ATTRIBUTE",
                    fontSize = 14.sp
                )

                AttributeType.entries.forEach { type ->
                    TypeOptionCard(
                        type = type,
                        onClick = {
                            onTypeSelected(type)
                            onDismissRequest()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TypeOptionCard(
    type: AttributeType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, Color.Black),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = type.icon,
                contentDescription = type.label,
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = type.label,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun previewAttributeTypeDialog(){
    AttributeTypeDialog(
        onDismissRequest = {},
        onTypeSelected = {}
    )
}
*/