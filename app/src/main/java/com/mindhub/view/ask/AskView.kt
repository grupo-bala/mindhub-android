package com.mindhub.view.ask

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.view.composables.post.PostView
import com.mindhub.viewmodel.ask.GetAskViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun AskView(
    askId: Int,
    navigator: DestinationsNavigator
) {
    val getViewModel: GetAskViewModel = viewModel()

    PostView(
        postId = askId,
        viewModel = getViewModel,
        navigator = navigator
    )
}