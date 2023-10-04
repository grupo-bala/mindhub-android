package com.mindhub.view.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.model.entities.User
import com.mindhub.services.ErrorParser
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.Suspended
import com.mindhub.view.destinations.AskDestination
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.viewmodel.register.ExpertiseViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Destination
@Composable
fun Expertises(
    user: User,
    password: String,
    navigator: DestinationsNavigator
) {
    val viewModel: ExpertiseViewModel = viewModel()
    
    viewModel.loadExpertises()

    Surface(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        SpacedColumn(
            spacing = 8,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .padding(32.dp)
        ) {
            Text(
                text = "Selecione as suas especialidades",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Suspended(
                isLoading = viewModel.expertises.size < 1,
                loadingSize = 64.dp,
                strokeWidth = 4.dp,
                color = MaterialTheme.colorScheme.onBackground
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    viewModel.expertises.forEach {
                        InputChip(
                            selected = viewModel.isSelected(it.title),
                            onClick = { viewModel.toggleExpertise(it.title) },
                            label = { Text(text = it.title) },
                        )
                    }
                }
            }

            if (viewModel.feedback != "") {
                Text(
                    text = viewModel.feedback,
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
                    viewModel.register(
                        user,
                        password,
                        onSuccess = { navigator.navigate(AskDestination) },
                        onFailure = { viewModel.feedback = ErrorParser.from(it) }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Suspended(isLoading = viewModel.isLoading) {
                    Text(text = "Cadastrar")
                }
            }

            OutlinedButton(
                onClick = { navigator.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Voltar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpertisePreview() {
    MindHubTheme {
        Expertises(
            User("", "", ""),
            "",
            EmptyDestinationsNavigator
        )
    }
}
