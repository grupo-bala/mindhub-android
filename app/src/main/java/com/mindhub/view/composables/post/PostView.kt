package com.mindhub.view.composables.post

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.Suspended
import com.mindhub.view.composables.comment.CommentsView
import com.mindhub.view.layouts.AppScaffold
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.view.layouts.Views
import com.mindhub.viewmodel.ask.GetAskViewModel
import com.mindhub.viewmodel.post.GetPostViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Composable
fun PostView(
    postId: Int,
    viewModel: GetPostViewModel,
    navigator: DestinationsNavigator
) {

    LaunchedEffect(key1 = true) {
        viewModel.get(postId)
    }

    AppScaffold(
        currentView = Views.ASK,
        navigator = navigator,
        hasBackArrow = true,
        bottomAppBar = {}
    ) {
        Suspended(
            isLoading = viewModel.isLoading
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                SpacedColumn(
                    spacing = 8,
                    verticalAlignment = Alignment.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    if (viewModel.post == null) {
                        Text(text = viewModel.feedback)
                    } else {
                        PostInfo(post = viewModel.post!!, navigator = navigator)
                        CommentsView(postId = postId)
                    }
                }

                if (viewModel.post != null) {
                    FloatingActionButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        Icon(imageVector = Icons.Outlined.Email, contentDescription = null)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostViewPreview() {
    val viewModel: GetAskViewModel = viewModel()

    MindHubTheme {
        PostView(
            navigator = EmptyDestinationsNavigator,
            postId = 0,
            viewModel = viewModel
        )
    }
}