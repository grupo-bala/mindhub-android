package com.mindhub.view.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.mindhub.R
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.NavBar
import com.mindhub.view.composables.TopBar
import com.mindhub.view.destinations.AskHomeDestination
import com.mindhub.view.destinations.EventFeedDestination
import com.mindhub.view.destinations.MaterialFeedDestination
import com.mindhub.view.destinations.ProfileDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction

enum class Views(val icon: Int, val destination: Direction) {
    ASK(R.drawable.baseline_question_answer_24, AskHomeDestination),
    MATERIAL(R.drawable.baseline_auto_stories_24, MaterialFeedDestination),
    EVENT(R.drawable.baseline_place_24, EventFeedDestination),
    USER(R.drawable.baseline_account_circle_24, ProfileDestination())
}

@Composable
fun AppScaffold(
    currentView: Views,
    navigator: DestinationsNavigator,
    hasBackArrow: Boolean = false,
    hasLogout: Boolean = false,
    topAppBar: @Composable () -> Unit = { TopBar(navigator, hasBackArrow, hasLogout) },
    bottomAppBar: @Composable (Views) -> Unit = {
        NavBar(currentView = currentView, navigator = navigator)
    },
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            topAppBar()
        },
        bottomBar = {
            bottomAppBar(currentView)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppScaffoldPreview() {
    MindHubTheme {
        AppScaffold (
            currentView = Views.ASK,
            navigator = EmptyDestinationsNavigator
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(text = "Scaffold")
            }
        }
    }
}