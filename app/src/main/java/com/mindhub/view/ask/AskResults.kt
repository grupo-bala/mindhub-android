package com.mindhub.view.ask

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.NavBar
import com.mindhub.view.composables.Suspended
import com.mindhub.view.composables.post.PostList
import com.mindhub.view.destinations.AskCreateDestination
import com.mindhub.view.destinations.AskViewDestination
import com.mindhub.view.layouts.AppScaffold
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.view.layouts.Views
import com.mindhub.viewmodel.ask.SearchAskViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import kotlinx.coroutines.launch

@Destination
@Composable
fun AskResults(
    navigator: DestinationsNavigator
) {
    val searchViewModel: SearchAskViewModel = viewModel()
    val input = searchViewModel.inputText.collectAsState()

    LaunchedEffect(key1 = true) {
        val lastInput = input.value
        searchViewModel.updateInput("")
        searchViewModel.updateInput(lastInput)
    }

    AppScaffold(
        currentView = Views.ASK,
        navigator = navigator,
        bottomAppBar = {
            Column {
                if (!searchViewModel.isFirstSearch) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 0.dp, bottomStart = 0.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(16.dp)
                    ) {
                        Column {
                            Text(
                                text = "Não é o que você procura?",
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.labelLarge
                            )

                            Text(
                                text = "Faça a sua pergunta",
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }

                        IconButton(
                            onClick = { navigator.navigate(AskCreateDestination(title = input.value)) },
                            modifier = Modifier.semantics {
                                contentDescription = "AddAskButton"
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp)
                            )
                        }
                    }
                }
                NavBar(currentView = Views.ASK, navigator = navigator)
            }
        }
    ) {
        SpacedColumn(
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.Start,
            spacing = 16,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            OutlinedTextField(
                leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
                placeholder = { Text(text = "Digite a sua dúvida") },
                value = input.value,
                onValueChange = {
                    searchViewModel.updateInput(it)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Resultados",
                style = MaterialTheme.typography.titleMedium
            )

            Suspended(
                isLoading = searchViewModel.isLoading
            ) {
                if (searchViewModel.isFirstSearch) {
                    Text(text = "Pesquise a sua dúvida")
                } else if (searchViewModel.asks.isEmpty()) {
                    Text(text = "Nenhum resultado foi encontrado")
                } else {
                    PostList(
                        posts = searchViewModel.asks,
                        onClick = { navigator.navigate(AskViewDestination(it.id)) },
                        isLoading = searchViewModel.isLoading
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AskResultsPreview() {
    MindHubTheme {
        AskResults(
            navigator = EmptyDestinationsNavigator
        )
    }
}