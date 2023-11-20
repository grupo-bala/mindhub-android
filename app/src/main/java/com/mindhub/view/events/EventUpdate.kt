package com.mindhub.view.events

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.LatLng
import com.mindhub.common.ext.getTime
import com.mindhub.common.ext.toBrazilianDateFormat
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Event
import com.mindhub.model.entities.User
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.events.EventFields
import com.mindhub.view.composables.post.PostUpdate
import com.mindhub.viewmodel.event.EventViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import java.time.LocalDateTime

@Destination
@Composable
fun EventUpdate(
    navigator: DestinationsNavigator,
    event: Event
) {
    val viewModel = EventViewModel()
    viewModel.title = event.title
    viewModel.content = event.content
    viewModel.date = event.date.toBrazilianDateFormat()
    viewModel.position = LatLng(event.latitude, event.longitude)
    viewModel.positionName = event.localName
    viewModel.time = event.date.getTime()

    PostUpdate(
        navigator = navigator,
        viewModel = viewModel,
        postId = event.id,
        onSuccess = { navigator.popBackStack() }
    ) {
        EventFields(viewModel = viewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun EventUpdatePreview() {
    MindHubTheme {
        val UserInfo = User("João", "joaum123@gmail.com", "jjaum", 0, Badge(""), listOf(), "")
        EventUpdate(navigator = EmptyDestinationsNavigator, event = Event(6, UserInfo, "Teste 10", userScore = 0, "teste", 2, LocalDateTime.now(), LocalDateTime.now(), 0.0, 0.0, "Quixadá"))

    }
}