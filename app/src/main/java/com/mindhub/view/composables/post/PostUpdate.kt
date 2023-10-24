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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindhub.viewmodel.post.PostViewModelInterface
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostUpdate(
    navigator: DestinationsNavigator,
    viewModel: PostViewModelInterface,
    postId: Int,
    onSuccess: () -> Unit,
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
                title = { Text(text = "Editar ${viewModel.getType()}", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = {
                        navigator.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = null)
                    }
                },
                actions = {
                    Button(onClick = {
                        viewModel.update(
                            onSuccess = onSuccess,
                            onFailure = {
                                feedbackError = it
                            },
                            postId = postId
                        )
                    }) {
                        Text(text = "Alterar")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            PostInputs(
                viewModel = viewModel,
                extraContent = extraFields
            )

            OutlinedButton(
                onClick = { viewModel.remove(
                    postId,
                    onSuccess = {
                        navigator.popBackStack()
                    },
                    onFailure = {
                        feedbackError = it
                    }
                ) },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Remover material",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

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