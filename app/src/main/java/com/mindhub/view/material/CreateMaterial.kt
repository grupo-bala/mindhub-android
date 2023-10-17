package com.mindhub.view.material

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.PostInputs
import com.mindhub.viewmodel.material.CreateMaterialViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun CreateMaterial(
    navigator: DestinationsNavigator
) {
    val viewModel = CreateMaterialViewModel()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            TopAppBar(
                title = { Text(text = "Adicionar um material", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = {
                        navigator.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = null)
                    }
                },
                actions = {
                    Button(onClick = {
                        viewModel.create(
                            onSuccess = {
                                TODO("Add navigator to material exhibition view")
                            },
                            onFailure = {
                                TODO("No errors are defined in the fake api")
                            }
                        )
                    }) {
                        Text(text = "Publicar")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            PostInputs(viewModel = viewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateMaterialPreview() {
    MindHubTheme {
        CreateMaterial(navigator = EmptyDestinationsNavigator)
    }
}