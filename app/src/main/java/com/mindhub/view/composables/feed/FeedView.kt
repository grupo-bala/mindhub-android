package com.mindhub.view.composables.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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
    onClickItem: (Post) -> Unit,
    onClickAdd: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.getRecents()
        viewModel.getForYou()
    }

    AppScaffold(
        currentView = currentView,
        navigator = navigator
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Tabs(
                tabs = listOf("Para você", "Mais recentes"),
                tabsContent = listOf({
                    Suspended(isLoading = viewModel.isLoadingForYou) {
                        PostList(
                            posts = viewModel.forYou,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            onClick = onClickItem
                        )
                    }
                }, {
                    Suspended(isLoading = viewModel.isLoadingRecents) {
                        PostList(
                            posts = viewModel.recents,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            onClick = onClickItem
                        )
                    }
                })
            )

            FloatingActionButton(
                onClick = onClickAdd,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .semantics {
                        contentDescription = "AddPostButton"
                    }
            ) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
            }
        }
    }
}