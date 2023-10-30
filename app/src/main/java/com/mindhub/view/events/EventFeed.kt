package com.mindhub.view.events

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.view.composables.Suspended
import com.mindhub.view.composables.Tabs
import com.mindhub.view.composables.post.PostList
import com.mindhub.view.layouts.AppScaffold
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

    LaunchedEffect(key1 = true) {
        feed.getForYou()
        feed.getRecents()
    }

    AppScaffold(
        currentView = Views.EVENT,
        navigator = navigator
    ) {
        Tabs(
            tabs = listOf("Para você", "Mais recentes"),
            tabsContent = listOf({
                Suspended(isLoading = feed.isLoadingForYou) {
                    PostList(
                        posts = feed.eventsForYou,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onClick = { }
                    )
                }
            }, {
                Suspended(isLoading = feed.isLoadingRecents) {
                    PostList(
                        posts = feed.eventsRecent,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onClick = { }
                    )
                }
            })
        )
    }
}