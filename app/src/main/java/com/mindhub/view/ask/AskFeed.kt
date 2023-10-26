package com.mindhub.view.ask

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.common.services.UserInfo
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.User
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.Suspended
import com.mindhub.view.composables.Tabs
import com.mindhub.view.composables.post.PostList
import com.mindhub.view.layouts.AppScaffold
import com.mindhub.view.layouts.Views
import com.mindhub.viewmodel.ask.FeedAskViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination
@Composable
fun AskFeed(
    navigator: DestinationsNavigator
) {
    val feed: FeedAskViewModel = viewModel()

    LaunchedEffect(key1 = true) {
        feed.getForYou()
        feed.getRecents()
    }

    AppScaffold(
        currentView = Views.ASK,
        navigator = navigator
    ) {
        Tabs(
            tabs = listOf("Para você", "Mais recentes"),
            tabsContent = listOf({
                Suspended(isLoading = feed.isLoadingForYou) {
                    PostList(
                        posts = feed.asksForYou,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onClick = { }
                    )
                }
            }, {
                Suspended(isLoading = feed.isLoadingRecents) {
                    PostList(
                        posts = feed.asksRecent,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onClick = { }
                    )
                }
            })
        )
    }
}

@Preview
@Composable
fun AskFeedPreview() {
    UserInfo = User(
        name = "User",
        username = "username",
        email = "user@gmail.com",
        xp = 727,
        currentBadge = Badge("Aprendiz"),
        expertises = listOf(Expertise("Matemática"), Expertise("Geografia"), Expertise("Química")),
        token = ""
    )

    MindHubTheme {
        AskFeed(navigator = EmptyDestinationsNavigator)
    }
}