package com.mindhub.view.ask

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.PostCreate
import com.mindhub.view.destinations.AskViewDestination
import com.mindhub.viewmodel.ask.AskViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination
@Composable
fun AskCreate(
    navigator: DestinationsNavigator,
    title: String? = null
) {
    val viewModel: AskViewModel = viewModel()
    viewModel.title = title ?: ""

    PostCreate(
        navigator = navigator,
        viewModel = viewModel,
        onSuccess = {
            navigator.popBackStack()
            navigator.navigate(AskViewDestination(it.id))
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AskCreatePreview() {
    MindHubTheme {
        AskCreate(navigator = EmptyDestinationsNavigator)
    }
}