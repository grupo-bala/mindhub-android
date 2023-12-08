package com.mindhub.view.events

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.view.composables.post.PostView
import com.mindhub.viewmodel.event.GetEventViewModel
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(
    route = "event",
    deepLinks = [
        DeepLink(
            uriPattern = "https://mindhub.netlify.app/event/{eventId}",
            action = Intent.ACTION_VIEW
        )
    ]
)
@Composable
fun EventView(
    eventId: Int,
    navigator: DestinationsNavigator
) {
    val getViewModel: GetEventViewModel = viewModel()

    PostView(
        postId = eventId,
        viewModel = getViewModel,
        navigator = navigator
    )
}