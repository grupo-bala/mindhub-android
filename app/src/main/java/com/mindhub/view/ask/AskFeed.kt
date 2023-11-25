package com.mindhub.view.ask

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.common.services.UserInfo
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.User
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.feed.FeedView
import com.mindhub.view.destinations.AskResultsDestination
import com.mindhub.view.destinations.AskViewDestination
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

    FeedView(
        navigator = navigator,
        viewModel = feed,
        currentView = Views.ASK,
        onClickItem = { navigator.navigate(AskViewDestination(it.id)) },
        onClickAdd = { navigator.navigate(AskResultsDestination) }
    )
}

@Preview
@Composable
fun AskFeedPreview() {
    UserInfo = User(
        name = "User",
        username = "username",
        email = "user@gmail.com",
        xp = 727,
        currentBadge = Badge("Aprendiz", 0),
        badges = listOf(),
        expertises = listOf(Expertise("Matemática"), Expertise("Geografia"), Expertise("Química")),
        token = ""
    )

    MindHubTheme {
        AskFeed(navigator = EmptyDestinationsNavigator)
    }
}