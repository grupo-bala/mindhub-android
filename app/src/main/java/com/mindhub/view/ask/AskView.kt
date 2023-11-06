package com.mindhub.view.ask

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.view.composables.post.PostView
import com.mindhub.viewmodel.ask.GetAskViewModel
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(
    route = "ask",
    deepLinks = [
        DeepLink(
            uriPattern = "https://mindhub.netlify.app/ask/{askId}",
            action = Intent.ACTION_VIEW
        )
    ]
)
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