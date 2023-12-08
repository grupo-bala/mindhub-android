package com.mindhub.view.events

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.view.composables.feed.FeedView
import com.mindhub.view.destinations.EventCreateDestination
import com.mindhub.view.destinations.EventViewDestination
import com.mindhub.view.layouts.Views
import com.mindhub.viewmodel.event.FeedEventViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun EventFeed(
    navigator: DestinationsNavigator
) {
    val feed: FeedEventViewModel = viewModel()

    FeedView(
        navigator = navigator,
        viewModel = feed,
        currentView = Views.EVENT,
        onClickItem = { navigator.navigate(EventViewDestination(it.id)) },
        onClickAdd = { navigator.navigate(EventCreateDestination) }
    )
}