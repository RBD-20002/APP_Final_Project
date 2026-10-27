package com.example.finalproject.component

import android.content.Context
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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import java.io.File

@Composable
fun CamImageSelector(
    selectImageUri: Uri?,
    defaultCategoryIcon: ImageVector,
    onImageSelected: (Uri?) -> Unit,
    modifier: Modifier = Modifier,
    isReadOnly: Boolean = false
) {
    val context = LocalContext.current
    val authority = "${context.packageName}.fileprovider"
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) onImageSelected(uri)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            onImageSelected(tempCameraUri)
        }
    }

    Box(
        modifier = modifier.size(180.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Color.DarkGray)
                .border(2.dp, Color.Black, RoundedCornerShape(18.dp))
                .clickable(enabled = !isReadOnly) { galleryLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (selectImageUri != null) {
                AsyncImage(
                    model = selectImageUri,
                    contentDescription = "IMAGEN DE PRODUCTO",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = defaultCategoryIcon,
                    contentDescription = "ICONO POR CATEGORIA",
                    tint = Color.Black,
                    modifier = Modifier.size(60.dp)
                )
            }
        }

        if (!isReadOnly) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = (-6).dp)
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                    .clickable {
                        val uri = context.createTempPictureUri(authority)
                        tempCameraUri = uri
                        cameraLauncher.launch(uri)
                    },
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
}

private fun Context.createTempPictureUri(provider: String): Uri {
    val tempFile = File.createTempFile(
        "IMG_${System.currentTimeMillis()}",
        ".jpg",
        cacheDir
    ).apply {
        createNewFile()
        deleteOnExit()
    }
    return FileProvider.getUriForFile(this, provider, tempFile)
}