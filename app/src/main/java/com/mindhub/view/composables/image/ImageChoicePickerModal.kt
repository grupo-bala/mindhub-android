package com.mindhub.view.composables.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mindhub.R
import com.mindhub.view.layouts.SpacedColumn

@Composable
fun ImageChoicePickerModal(
    galleryLauncher:() -> Unit,
    cameraLauncher: () -> Unit,
    modalController: (Boolean) -> Unit
) {
    Dialog(onDismissRequest = { modalController(false) }) {
        Card(
            modifier = Modifier.height(100.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Button(
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        cameraLauncher()
                        modalController(false)
                    }
                ) {
                    SpacedColumn(spacing = 8, verticalAlignment = Alignment.CenterVertically, horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(imageVector = ImageVector.vectorResource(R.drawable.photo), contentDescription = null)
                        Text(text = "Tirar foto")
                    }
                }

                Spacer(modifier = Modifier.size(16.dp))

                Button(
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        galleryLauncher()
                        modalController(false)
                    }
                ) {
                    SpacedColumn(spacing = 8, verticalAlignment = Alignment.CenterVertically, horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(imageVector = ImageVector.vectorResource(R.drawable.image), contentDescription = null)
                        Text(text = "Galeria")
                    }
                }
            }
        }
    }
}