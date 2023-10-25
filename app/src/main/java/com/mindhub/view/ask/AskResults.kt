package com.mindhub.view.ask

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.model.api.AskFakeApi
import com.mindhub.model.entities.Ask
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.User
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

    AppScaffold(
        currentView = Views.ASK,
        navigator = navigator,
        bottomAppBar = {
            Column {
                if (searchViewModel.asks.isEmpty() && !searchViewModel.isFirstSearch) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.large)
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

                        IconButton(onClick = { navigator.navigate(AskCreateDestination(title = searchViewModel.inputTitle)) }) {
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
                value = searchViewModel.inputTitle,
                onValueChange = {
                    searchViewModel.inputTitle = it
                    searchViewModel.isFirstSearch = false
                    searchViewModel.get {  }
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
                        onClick = { navigator.navigate(AskViewDestination(it.id)) }
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