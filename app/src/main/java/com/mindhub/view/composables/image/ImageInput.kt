package com.mindhub.view.composables.image

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mindhub.R
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.layouts.SpacedColumn

@Composable
fun ImageInput(
    urlImage: String? = null,
    setImage: (Bitmap?) -> Unit
) {
    var currentUrl by remember {
        mutableStateOf(urlImage)
    }

    var image by remember {
        mutableStateOf<Bitmap?>(null)
    }

    val context = LocalContext.current

    var isModalOpen by remember {
        mutableStateOf(false)
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            image = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it!!)
                ImageDecoder.decodeBitmap(source)
            }
            setImage(image)
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = {
            image = it
            setImage(image)
        }
    )

    var buttonLabelFile by remember {
        mutableStateOf("")
    }

    SpacedColumn(
        spacing = 8,
        verticalAlignment = Alignment.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Row {
            Button(
                onClick = { isModalOpen = true },
                colors = ButtonDefaults.buttonColors(),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.upload),
                    contentDescription = null,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
                Text(text = buttonLabelFile)
            }

            if (currentUrl != null || image != null) {
                buttonLabelFile = "Editar foto"

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        currentUrl = null
                        image = null
                        buttonLabelFile = "Adicionar foto"
                        setImage(image)
                    },
                    shape = RoundedCornerShape(5.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = null,
                        modifier = Modifier.padding(vertical = 3.dp)
                    )
                }
            } else {
                buttonLabelFile = "Adicionar foto"
            }
        }

        if (currentUrl != null || image != null) {
            AsyncImage(
                model = image ?: currentUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(5))
            )
        }

        if (isModalOpen) {
            ImageChoicePickerModal(
                galleryLauncher = { galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                cameraLauncher = { cameraLauncher.launch() },
                modalController = { isModalOpen = it }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageInputPreview() {
    var image: Bitmap? = null

    MindHubTheme {
        ImageInput() { image = it }
    }
}