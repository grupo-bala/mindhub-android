package com.mindhub.view.ask

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.Tabs
import com.mindhub.view.layouts.AppScaffold
import com.mindhub.view.layouts.Views
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination
@Composable
fun AskFeed(
    navigator: DestinationsNavigator
) {
    AppScaffold(
        currentView = Views.ASK,
        navigator = navigator
    ) {
        Tabs(
            tabs = listOf("Para você", "Mais recentes"),
            tabsContent = listOf({}, {})
        )
    }
}

@Preview
@Composable
fun AskFeedPreview() {
    MindHubTheme {
        AskFeed(navigator = EmptyDestinationsNavigator)
    }
}