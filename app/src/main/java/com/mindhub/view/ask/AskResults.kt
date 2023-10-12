package com.mindhub.view.ask

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.layouts.AppScaffold
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.view.layouts.Views
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun AskResults() {
    AppScaffold(currentView = Views.ASK) {
        SpacedColumn(
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.Start,
            spacing = 8,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
                placeholder = { Text(text = "Digite a sua dúvida") },
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth()
            )

            Text(text = "Resultados")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AskResultsPreview() {
    MindHubTheme {
        AskResults()
    }
}