package com.mindhub.view.events

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mindhub.common.services.CurrentUser
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.User
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.events.EventFields
import com.mindhub.view.composables.post.PostCreate
import com.mindhub.view.destinations.EventViewDestination
import com.mindhub.viewmodel.event.EventViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator


@Destination
@Composable
fun EventCreate(
    navigator: DestinationsNavigator
) {
    val viewModel = EventViewModel()

    PostCreate(
        navigator = navigator,
        viewModel = viewModel,
        onSuccess = {
            navigator.navigate(EventViewDestination(it.id))
        }
    ) {
        EventFields(viewModel = viewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun CreateEventInfoPreview() {
    CurrentUser.user = User(
        name = "User",
        username = "username",
        email = "user@gmail.com",
        xp = 727,
        currentBadge = Badge("Aprendiz", 0),
        badges = listOf(),
        expertises = listOf(Expertise("Matemática"), Expertise("Geografia"), Expertise("Química"))
    )

    MindHubTheme {
        EventCreate(navigator = EmptyDestinationsNavigator)
    }
}