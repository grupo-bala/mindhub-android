package com.mindhub.view.material
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.post.PostCreate
import com.mindhub.view.destinations.MaterialViewDestination
import com.mindhub.viewmodel.material.MaterialViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination
@Composable
fun MaterialCreate(
    navigator: DestinationsNavigator,
    title: String? = null
) {
    val viewModel: MaterialViewModel = viewModel()
    viewModel.title = title ?: ""

    PostCreate(
        navigator = navigator,
        viewModel = viewModel,
        onSuccess = {
            navigator.navigate(MaterialViewDestination(it.id))
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CreateMaterialPreview() {
    MindHubTheme {
        MaterialCreate(navigator = EmptyDestinationsNavigator)
    }
}