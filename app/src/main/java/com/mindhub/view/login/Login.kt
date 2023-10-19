package com.mindhub.view.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.services.ErrorParser
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.Suspended
import com.mindhub.view.destinations.AskHomeDestination
import com.mindhub.view.destinations.RegisterDestination
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.viewmodel.login.LoginViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
@OptIn(ExperimentalMaterial3Api::class)
@Destination(start = true)
@Composable
fun Login(
    navigator: DestinationsNavigator
) {
    val viewModel: LoginViewModel = viewModel()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SpacedColumn(
            spacing = 8,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .padding(48.dp)
        ) {
            Text(
                text = "MindHub",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = viewModel.email,
                label = { Text(text = "Email") },
                placeholder = { Text(text = "Digite o seu email") },
                onValueChange = { viewModel.email = it },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.password,
                label = { Text(text = "Senha") },
                placeholder = { Text(text = "Digite a sua senha") },
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { viewModel.password = it },
                modifier = Modifier.fillMaxWidth()
            )

            if (viewModel.feedback != "") {
                Text(
                    text = viewModel.feedback,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Button(
                onClick = {
                    viewModel.login(
                        onSuccess = {
                            navigator.navigate(AskHomeDestination)
                        },
                        onFailure = {
                            println(it)
                            viewModel.feedback = ErrorParser.from(it)
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Suspended(isLoading = viewModel.isLoading) {
                    Text(text = "Entrar")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "Ainda não é cadastrado?")
                Text(
                    text = "Crie sua conta aqui",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { navigator.navigate(RegisterDestination) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    MindHubTheme {
        Login(navigator = EmptyDestinationsNavigator)
    }
}