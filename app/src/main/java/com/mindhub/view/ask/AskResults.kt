package com.mindhub.view.ask

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindhub.model.entities.Ask
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.User
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.PostItem
import com.mindhub.view.layouts.AppScaffold
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.view.layouts.Views
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination
@Composable
fun AskResults(
    navigator: DestinationsNavigator
) {
    AppScaffold(
        currentView = Views.ASK,
        navigator = navigator
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
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Resultados",
                style = MaterialTheme.typography.titleMedium
            )
            val user = User("", "", "", 0, Badge(""), listOf(), "")
            val values = listOf<Ask>(
                Ask(0, "Teste 1", "teste", Expertise("teste"), 76, user),
                Ask(1, "Teste 2", "teste teste", Expertise("teste"), 43, user),
                Ask(2, "Teste 3", "teste teste teste", Expertise("teste"), 234, user),
                Ask(3, "Teste 4", "teste teste teste teste", Expertise("teste"), -23, user),
            )

            LazyColumn {
                items(values) {
                    PostItem(title = it.title, description = it.content, score = it.score)
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