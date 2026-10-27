package com.example.finalproject.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.finalproject.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteConfirmDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    title: String  ?= stringResource(id = R.string.dialog_delete_title),
    description: String ?= stringResource(id = R.string.dialog_delete_description)
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.LightGray,
        title = {
            if (title != null) {
                Text(
                    text = title,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        },
        text = {
            if (description != null) {
                Text(
                    text = description,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        confirmButton = {
            ActionButtons(
                cancelText = stringResource(id = R.string.btn_cancel),
                submitText = stringResource(id = R.string.btn_delete),
                onCancel = onDismiss,
                onSubmit = onConfirm,
                modifier = Modifier.fillMaxWidth()
            )
        },
        dismissButton = null,
        modifier = modifier
    )
}