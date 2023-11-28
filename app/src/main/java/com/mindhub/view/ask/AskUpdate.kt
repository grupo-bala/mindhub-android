package com.mindhub.view.ask

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mindhub.common.services.CurrentUser
import com.mindhub.model.entities.Ask
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.User
import com.mindhub.view.composables.FileInput
import com.mindhub.view.composables.post.PostUpdate
import com.mindhub.viewmodel.ask.AskViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import java.time.LocalDateTime

@Destination
@Composable
fun AskUpdate(
    navigator: DestinationsNavigator,
    ask: Ask
) {
    var viewModel = AskViewModel()
    viewModel.title = ask.title
    viewModel.content = ask.content
    viewModel.expertise = ask.expertise
    viewModel.file = ask.file

    PostUpdate(
        navigator = navigator,
        viewModel = viewModel,
        postId = ask.id,
        onSuccess = { navigator.popBackStack() }
    ) {
        FileInput(askViewModel = viewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun AskUpdatePreview() {
    CurrentUser.user = User(
        name = "User",
        username = "username",
        email = "user@gmail.com",
        xp = 727,
        currentBadge = Badge("Aprendiz", 0),
        badges = listOf(),
        expertises = listOf(Expertise("Matemática"), Expertise("Geografia"), Expertise("Química"))
    )

    var ask = Ask(
        id = 1,
        title = "Soma de raízes",
        content = "Como somar duas raízes quadradas",
        expertise = Expertise("Matemática"),
        user = CurrentUser.user!!,
        postDate = LocalDateTime.now(),
        score = 0,
        userScore = 0,
    )

    MaterialTheme {
        AskUpdate(navigator = EmptyDestinationsNavigator, ask = ask)
    }
}