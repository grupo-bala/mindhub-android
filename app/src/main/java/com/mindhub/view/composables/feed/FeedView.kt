package com.mindhub.view.composables.feed

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindhub.model.entities.Post
import com.mindhub.view.composables.Suspended
import com.mindhub.view.composables.Tabs
import com.mindhub.view.composables.post.PostList
import com.mindhub.view.layouts.AppScaffold
import com.mindhub.view.layouts.Views
import com.mindhub.viewmodel.post.FeedPostViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun FeedView(
    navigator: DestinationsNavigator,
    viewModel: FeedPostViewModel,
    currentView: Views,
    onClick: (Post) -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.getForYou()
        viewModel.getRecents()
    }

    AppScaffold(
        currentView = currentView,
        navigator = navigator
    ) {
        Tabs(
            tabs = listOf("Para você", "Mais recentes"),
            tabsContent = listOf({
                Suspended(isLoading = viewModel.isLoadingForYou) {
                    PostList(
                        posts = viewModel.forYou,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onClick = onClick
                    )
                }
            }, {
                Suspended(isLoading = viewModel.isLoadingRecents) {
                    PostList(
                        posts = viewModel.recents,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onClick = onClick
                    )
                }
            })
        )
    }
}