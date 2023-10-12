package com.mindhub.view.ask

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.User
import com.mindhub.services.UserInfo
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.layouts.AppScaffold
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.view.layouts.Views
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun Ask() {
    AppScaffold(currentView = Views.ASK) {
        SpacedColumn(
            spacing = 16,
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Olá, ${UserInfo!!.name}!")

            Text(
                text = "O que você precisa saber?",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            OutlinedTextField(
                leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
                placeholder = { Text(text = "Digite a sua dúvida") },
                value = "",
                onValueChange = {}
            )

            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "ou quer responder umas perguntas?")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AskPreview() {
    UserInfo = User(
        name = "Pedro",
        email = "pedro@gmail.com",
        username = "pedro123",
        xp = 0,
        currentBadge = Badge("Teste"),
        expertises = listOf(),
        token = ""
    )

    MindHubTheme {
        Ask()
    }
}