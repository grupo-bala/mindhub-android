package com.mindhub.view.login

import android.app.Activity
import android.os.Build
import android.widget.CheckBox
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.common.services.ErrorParser
import com.mindhub.common.services.StoreData
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.NavGraphs
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
    val context = LocalContext.current

    val executor = remember {
        ContextCompat.getMainExecutor(context)
    }

    val biometricPrompt = BiometricPrompt(
        context as FragmentActivity,
        executor,
        object : BiometricPrompt.AuthenticationCallback() {
            @RequiresApi(Build.VERSION_CODES.R)
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)

                try {
                    viewModel.setSavedUser(context) {
                        navigator.navigate(AskHomeDestination)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()

                Toast.makeText(context, "Autenticação inválida, tente novamente", Toast.LENGTH_LONG)
                    .show()
            }
        }
    )

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
        .setTitle("Autenticação biométrica")
        .setSubtitle("Faça login usando sua biometria")
        .build()

    LaunchedEffect(key1 = true) {
        val storeData = StoreData(context)
        storeData.getToken.collect {
            if (!it.isNullOrEmpty()) {
                biometricPrompt.authenticate(promptInfo)
            }
        }
    }


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

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = viewModel.rememberMe, onCheckedChange = { viewModel.rememberMe = it })
                Text(text = "Lembrar acesso")
            }

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
                        context = context,
                        onSuccess = {
                            navigator.navigate(AskHomeDestination)
                        },
                        onFailure = {
                            viewModel.feedback = ErrorParser.from(it)
                        }
                    )
                },
                enabled = viewModel.email != "" && viewModel.password != "",
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