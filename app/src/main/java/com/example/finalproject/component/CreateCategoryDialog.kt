package com.example.finalproject.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class CategoryIconOption(
    val id: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCategoryDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (name: String, iconKey: String) -> Unit,
    modifier: Modifier = Modifier
){
    var categoryName by remember { mutableStateOf("") }

    val iconOptions = listOf(
        CategoryIconOption("Clothing", Icons.Default.Checkroom),
        CategoryIconOption("Food", Icons.Default.Restaurant),
        CategoryIconOption("Tools", Icons.Default.Build),
        CategoryIconOption("Electronics", Icons.Default.Devices),
        CategoryIconOption("Health", Icons.Default.LocalHospital),
        CategoryIconOption("General", Icons.Default.Category)
    )

    var selectedIcon by remember { mutableStateOf(iconOptions.first()) }

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "NEW CATEGORY",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    iconOptions.forEach { option ->
                        val isSelected = option.id == selectedIcon.id
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(width = 2.dp, color = Color.Black),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) Color.Black else Color.White
                            ),
                            modifier = Modifier
                                .size(44.dp)
                                .clickable { selectedIcon = option}
                        ){
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ){
                                Icon(
                                    imageVector = option.icon,
                                    contentDescription = option.id,
                                    modifier = Modifier.size(24.dp),
                                    tint = if (isSelected) Color.White else Color.Black
                                )
                            }
                        }
                    }
                }
                OutlinedTextField(
                    value = categoryName,
                    onValueChange = { categoryName = it },
                    label = { Text("CATEGORY NAME") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                ActionButtons(
                    onCancel = onDismissRequest,
                    onSubmit = {
                        if(categoryName.isNotBlank()){
                            onConfirmation(categoryName, selectedIcon.id)
                        }
                    },
                    cancelText = "CANCEL",
                    submitText = "CREATE",
                    isSubmitEnabled = categoryName.isNotBlank()
                )
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun PreviewCreateCategoryDialog(){
    CreateCategoryDialog(
        onDismissRequest = {},
        onConfirmation = {_, _ ->}
    )
}
*/