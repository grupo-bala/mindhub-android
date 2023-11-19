package com.mindhub.view.register

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.common.services.ErrorParser
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.Suspended
import com.mindhub.view.destinations.AskHomeDestination
import com.mindhub.view.destinations.LoginDestination
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.viewmodel.expertise.ExpertiseViewModel
import com.mindhub.viewmodel.register.RegisterViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Destination
@Composable
fun Register(
    navigator: DestinationsNavigator
) {
    val registerViewModel: RegisterViewModel = viewModel()
    val expertiseViewModel: ExpertiseViewModel = viewModel()

    var isExpertisesMenuExpanded by remember {
        mutableStateOf(false)
    }

    val expertisesMenuInteraction = remember { MutableInteractionSource() }.also {
        if (it.collectIsFocusedAsState().value) {
            isExpertisesMenuExpanded = true
        }
    }

    expertiseViewModel.loadExpertises()

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
                value = registerViewModel.name,
                label = { Text(text = "Nome*") },
                placeholder = { Text(text = "Digite o seu nome") },
                onValueChange = { registerViewModel.name = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = registerViewModel.email,
                label = { Text(text = "Email*") },
                placeholder = { Text(text = "Digite o seu email") },
                onValueChange = { registerViewModel.email = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = registerViewModel.username,
                label = { Text(text = "Nome de usuário*") },
                placeholder = { Text(text = "Digite o seu nome de usuário") },
                onValueChange = { registerViewModel.username = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = expertiseViewModel.selectedExpertises.fold("") { acc, curr ->
                    if (acc.isEmpty()) {
                        curr.title
                    } else {
                        "$acc, ${curr.title}"
                    }
                },
                readOnly = true,
                label = { Text(text = "Expertises*") },
                onValueChange = {},
                placeholder = { Text(text = "Selecione suas expertises") },
                trailingIcon = { Icon(imageVector = Icons.Default.Create, contentDescription = null) },
                interactionSource = expertisesMenuInteraction,
                modifier = Modifier.fillMaxWidth()
            )

            if (isExpertisesMenuExpanded) {
                ModalBottomSheet(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 254.dp),
                    onDismissRequest = { isExpertisesMenuExpanded = false }
                ) {
                    Suspended(
                        isLoading = expertiseViewModel.expertises.size < 1,
                        loadingSize = 64.dp,
                        strokeWidth = 4.dp,
                        color = MaterialTheme.colorScheme.onBackground
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement
                                .spacedBy(
                                    8.dp,
                                    alignment = Alignment.CenterHorizontally
                                ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 32.dp,
                                    vertical = 32.dp
                                ),
                        ) {
                            expertiseViewModel.expertises.forEach {
                                InputChip(
                                    selected = expertiseViewModel.isSelected(it),
                                    onClick = { expertiseViewModel.toggleExpertise(it) },
                                    label = { Text(text = it.title) },
                                )
                            }
                        }
                    }
                }
            }

            OutlinedTextField(
                value = registerViewModel.password,
                label = { Text(text = "Senha*") },
                placeholder = { Text(text = "Digite a sua senha") },
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { registerViewModel.password = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = registerViewModel.passwordConfirmation,
                label = { Text(text = "Confirmar senha*") },
                placeholder = { Text(text = "Digite a sua senha novamente") },
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { registerViewModel.passwordConfirmation = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            if (registerViewModel.feedback != "") {
                Text(
                    text = registerViewModel.feedback,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(
                onClick = {
                    registerViewModel.register(
                        expertiseViewModel.selectedExpertises,
                        onSuccess = { navigator.navigate(AskHomeDestination) },
                        onFailure = { registerViewModel.feedback = ErrorParser.from(it) }
                    )
                },
                enabled = registerViewModel.isFilled() && expertiseViewModel.isFilled(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Confirmar")
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