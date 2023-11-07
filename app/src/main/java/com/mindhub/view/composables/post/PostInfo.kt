package com.mindhub.view.composables.post

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mindhub.common.ext.getRoute
import com.mindhub.common.services.UserInfo
import com.mindhub.model.entities.Ask
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Event
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.Material
import com.mindhub.model.entities.Post
import com.mindhub.model.entities.User
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.chips.CommentsChip
import com.mindhub.view.composables.chips.EditChip
import com.mindhub.view.composables.chips.LocationChip
import com.mindhub.view.composables.chips.ScoreChip
import com.mindhub.view.composables.chips.ShareChip
import com.mindhub.view.destinations.AskUpdateDestination
import com.mindhub.view.destinations.EventCreateDestination
import com.mindhub.view.destinations.UpdateMaterialDestination
import com.mindhub.view.layouts.SpacedColumn
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import io.ktor.util.reflect.instanceOf
import java.time.LocalDateTime

@Composable
fun PostInfo(
    post: Post,
    navigator: DestinationsNavigator
) {
    SpacedColumn(
        spacing = 8,
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = post.title, style = MaterialTheme.typography.titleLarge)
        Text(text = "por ${post.user.username}", style = MaterialTheme.typography.labelLarge)

        var destination: Direction = EventCreateDestination
        var askFile: Uri? = null

        Row {
            if (post.instanceOf(Ask::class)) {
                val postWithExpertise = post as Ask

                SuggestionChip(
                    onClick = { /*TODO*/ },
                    label = { Text(text = postWithExpertise.expertise.title) }
                )

                askFile = post.file
                destination = AskUpdateDestination(post)
            } else if (post.instanceOf(Material::class)) {
                val postWithExpertise = post as Material

                SuggestionChip(
                    onClick = { /*TODO*/ },
                    label = { Text(text = postWithExpertise.expertise.title) }
                )

                destination = UpdateMaterialDestination(postWithExpertise)
            }
        }

        Text(text = post.content)
        Spacer(modifier = Modifier.size(8.dp))

        if (askFile != null) {
            AsyncImage(
                model = askFile,
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .clip(RoundedCornerShape(5))
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.size(8.dp))
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            ScoreChip(score = post.score)
            CommentsChip(commentsQuantity = 10)

            if (post.instanceOf(Event::class)) {
                val event = post as Event
                LocationChip(
                    latitude = event.latitude,
                    longitude = event.longitude
                )
            }

            ShareChip(text = "${post.title}\n\n${post.getRoute()}")

            if (post.user == UserInfo) {
                EditChip {
                    navigator.navigate(destination)
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1.0f)
                    .height(2.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            Spacer(modifier = Modifier.size(16.dp))
            Text(text = "10 comentários", style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostInfoPreview() {
    val user = User("", "", "teste76", 0, Badge(""), listOf(), "")
    val ask = Ask(
        id = 0,
        title = "Produto das raízes com equação de 2 grau",
        content = "Gostaria de saber o produto das raízes das seguintes funções de segundo grau:\n\n" +
                "a)3x² - 6x + 1 = 0\n" +
                "b)x² - 16x + 28 = 0\n" +
                "c)2x² - 4x - 3 = 0 \n" +
                "d)2x² - 7x + 1 = 0",
        expertise = Expertise("Matemática"),
        score = 76,
        postDate = LocalDateTime.now(),
        user = user
    )

    MindHubTheme {
        PostInfo(post = ask, navigator = EmptyDestinationsNavigator)
    }
}