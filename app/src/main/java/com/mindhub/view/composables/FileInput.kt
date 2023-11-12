package com.mindhub.view.composables

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mindhub.R
import com.mindhub.model.entities.Expertise
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.viewmodel.ask.AskViewModel

@Composable
fun FileInput(
    askViewModel: AskViewModel
) {

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            askViewModel.file = it
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
                onClick = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
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

            if (askViewModel.file != null) {
                buttonLabelFile = "Editar anexo"

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        askViewModel.file = null
                        buttonLabelFile = "Adicionar anexo"
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
                buttonLabelFile = "Adicionar anexo"
            }
        }

        if (askViewModel.file != null) {
            AsyncImage(
                model = askViewModel.file,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(5))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FileInputPreview() {
    val ask = AskViewModel()

    ask.title = "Como somar suas raízes"
    ask.content = "Não sei como somar duas raízes quadradas, alguém pode me ajudar?"
    ask.expertise = Expertise("Matemática")

    MindHubTheme {
        FileInput(ask)
    }
}