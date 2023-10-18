package com.mindhub.view.material

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindhub.model.entities.Expertise
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.PostInputs
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.viewmodel.material.UpdateMaterialViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun UpdateMaterial(
    navigator: DestinationsNavigator,
    materialViewModel: UpdateMaterialViewModel
) {
    var feedbackError: String? = null

    Surface(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            TopAppBar(
                title = { Text(text = "Editar um material", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = {
                        navigator.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = null)
                    }
                },
                actions = {
                    Button(onClick = {
                        materialViewModel.update(
                            onSuccess = {
                                TODO("Add navigator to material exhibition view")
                            },
                            onFailure = {
                                feedbackError = it
                            }
                        )
                    }) {
                        Text(text = "Alterar")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SpacedColumn(
                spacing = 16,
                verticalAlignment = Alignment.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PostInputs(viewModel = materialViewModel)

                if (feedbackError != null) {
                    Text(
                        text = feedbackError!!,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UpdateMaterialPreview() {
    MindHubTheme {
        val viewModel = UpdateMaterialViewModel()
        viewModel.id = 1
        viewModel.title = "Soma de raízes"
        viewModel.content = "Mussum Ipsum, cacilds vidis litro abertis. Quem num gosta di mim que vai caçá sua turmis! Nec orci ornare consequat. Praesent lacinia ultrices consectetur. Sed non ipsum felis. Bota 1 metro de cachacis aí pra viagem! Admodum accumsan disputationi eu sit. Vide electram sadipscing et per.Mussum Ipsum, cacilds vidis litro abertis. Quem num gosta di mim que vai caçá sua turmis! Nec orci ornare consequat. Praesent lacinia ultrices consectetur. Sed non ipsum felis. Bota 1 metro de cachacis aí pra viagem! Admodum accumsan disputationi eu sit. Vide electram sadipscing et per.Mussum Ipsum, cacilds vidis litro abertis. Quem num gosta di mim que vai caçá sua turmis! Nec orci ornare consequat. Praesent lacinia ultrices consectetur. Sed non ipsum felis. Bota 1 metro de cachacis aí pra viagem! Admodum accumsan disputationi eu sit. Vide electram sadipscing et per."
        viewModel.expertise = Expertise("Matemática")

        UpdateMaterial(
            navigator = EmptyDestinationsNavigator,
            materialViewModel = viewModel
        )
    }
}