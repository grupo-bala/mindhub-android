package com.mindhub.view.composables.post

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mindhub.model.entities.Post
import com.mindhub.viewmodel.post.PostViewModelInterface
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCreate(
    navigator: DestinationsNavigator,
    viewModel: PostViewModelInterface,
    onSuccess: (Post) -> Unit,
    extraFields: @Composable () -> Unit = {}
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
                title = { Text(text = "Adicionar um ${viewModel.getType()}", style = MaterialTheme.typography.titleMedium) },
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
                                navigator.popBackStack()
                                onSuccess(it)
                            },
                            onFailure = {
                                feedbackError = it
                            }
                        )
                    }) {
                        Text(text = "Publicar")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            PostInputs(
                viewModel = viewModel,
                extraContent = extraFields
            )

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