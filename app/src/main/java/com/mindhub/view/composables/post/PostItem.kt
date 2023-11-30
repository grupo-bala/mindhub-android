package com.mindhub.view.composables.post

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindhub.R
import com.mindhub.common.ext.ellipsis
import com.mindhub.common.ext.toBrazilianDateFormat
import com.mindhub.model.entities.Ask
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Event
import com.mindhub.model.entities.Post
import com.mindhub.model.entities.User
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.chips.BaseChip
import io.ktor.util.reflect.instanceOf
import java.time.LocalDateTime

@Composable
fun PostItem(
    post: Post,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1.0f)
            ) {
                if (post.instanceOf(Ask::class)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val askPost = post as Ask

                        if (askPost.hasBestAnswer) {
                            Icon(
                                imageVector = Icons.Outlined.Check,
                                contentDescription = null,
                                tint = Color.Green,
                                modifier = Modifier.size(16.dp)
                            )

                            Spacer(modifier = Modifier.width(2.dp))

                            Text(
                                text = "Respondida",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Green,
                            )
                        } else {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.clock),
                                tint = Color.Gray,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )

                            Text(
                                text = "Aguardando resposta...",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = post.content.ellipsis(100),
                    style = MaterialTheme.typography.bodyMedium
                )

                if (post.instanceOf(Event::class)) {
                    val event = post as Event
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        BaseChip {
                            Icon(
                                imageVector = Icons.Outlined.DateRange,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )

                            Text(
                                text = event.date.toBrazilianDateFormat(),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }

                        BaseChip {
                            Icon(
                                imageVector = Icons.Outlined.LocationOn,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )

                            Text(
                                text = event.localName,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "${post.score}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Divider()
    }
}

@Preview(showBackground = true)
@Composable
fun PostItemPreview() {
    val user = User("", "", "teste76", 0, Badge("", 0, 0), listOf(), listOf(), null)
    val event = Event(
        id = 0,
        title = "Produto das raízes com equação de 2 grau",
        content = "Gostaria de saber o produto das raízes das seguintes funções de segundo grau:\n\n" +
                "a)3x² - 6x + 1 = 0\n" +
                "b)x² - 16x + 28 = 0\n" +
                "c)2x² - 4x - 3 = 0 \n" +
                "d)2x² - 7x + 1 = 0",
        score = 76,
        postDate = LocalDateTime.now(),
        date = LocalDateTime.now(),
        latitude = 0.0,
        longitude = 0.0,
        user = user,
        userScore = 0,
        localName = "Quixadá"
    )

    MindHubTheme {
        PostItem(
            post = event,
            onClick = {}
        )
    }
}