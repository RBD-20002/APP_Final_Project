package com.example.finalproject.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CamImageSelector(
    selectImageUri: Uri?,
    defaultCategoryIcon: ImageVector,
    onImageSelected: (Uri?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val photoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onImageSelected(uri)
    }

    Box(
        modifier = modifier.size(180.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Color.DarkGray)
                .border(
                    2.dp,
                    Color.Black,
                    RoundedCornerShape(18.dp)
                )
                .clickable { photoLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (selectImageUri != null) {
                AsyncImage(
                    model = selectImageUri,
                    contentDescription = "PRODUCT IMAGE",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = defaultCategoryIcon,
                    contentDescription = "ICON FOR CATEGORY",
                    tint = Color.Black,
                    modifier = Modifier.size(60.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-6).dp)
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .border(
                    2.dp,
                    Color.Black,
                    RoundedCornerShape(8.dp)
                )
                .clickable { photoLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "CAMARA",
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
/*
@Preview(showBackground = true)
@Composable
fun previewCamImageSelector(){
    CamImageSelector(
        selectImageUri = null,
        defaultCategoryIcon = Icons.Default.Category,
        onImageSelected = {}
    )
}
*/