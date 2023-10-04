package com.mindhub.view.register

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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.model.entities.User
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.destinations.ExpertisesDestination
import com.mindhub.view.destinations.LoginDestination
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.viewmodel.register.RegisterViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun Register(
    navigator: DestinationsNavigator
) {
    val viewModel: RegisterViewModel = viewModel()

    Surface(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())){
        SpacedColumn(
            spacing = 8,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(48.dp)
                .fillMaxHeight()
        ) {
            Text(
                text = "Cadastro",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = viewModel.name,
                label = { Text(text = "Nome") },
                placeholder = { Text(text = "Digite o seu nome") },
                onValueChange = { viewModel.name = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.email,
                label = { Text(text = "Email") },
                placeholder = { Text(text = "Digite o seu email") },
                onValueChange = { viewModel.email = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.username,
                label = { Text(text = "Nome de usuário") },
                placeholder = { Text(text = "Digite o seu nome de usuário") },
                onValueChange = { viewModel.username = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.password,
                label = { Text(text = "Senha") },
                placeholder = { Text(text = "Digite a sua senha") },
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { viewModel.password = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.passwordConfirmation,
                label = { Text(text = "Confirmar senha") },
                placeholder = { Text(text = "Digite a sua senha novamente") },
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { viewModel.passwordConfirmation = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    navigator.navigate(
                        ExpertisesDestination(
                            user = User(viewModel.name, viewModel.email, viewModel.username),
                            password = viewModel.password
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Continuar")
            }

            OutlinedButton(
                onClick = { navigator.navigate(LoginDestination) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Cancelar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    MindHubTheme {
        Register(navigator = EmptyDestinationsNavigator)
    }
}