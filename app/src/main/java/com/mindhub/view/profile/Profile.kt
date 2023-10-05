package com.mindhub.view.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.User
import com.mindhub.services.UserInfo
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.MeasureViewWidth
import com.mindhub.view.destinations.EditProfileDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator


@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun Profile(
    navigator: DestinationsNavigator
) {
    var currentTabIndex by remember {
        mutableIntStateOf(0)
    }
    val tabs = listOf("Perguntas", "Materiais")

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://picsum.photos/200") // TODO: change with the user profile picture
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .width(128.dp)
                        .height(128.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        MeasureViewWidth(viewToMeasure = {
                            Text(text = UserInfo!!.name)
                        }) { width ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = UserInfo!!.name)
                                IconButton(
                                    onClick = { navigator.navigate(EditProfileDestination) },
                                    modifier = Modifier.padding(start = width + 32.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Create, contentDescription = null)
                                }
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterHorizontally)
                        ) {
                            Text(text = "@${UserInfo!!.username}")

                            Divider(
                                modifier = Modifier
                                    .height(15.dp)
                                    .width(2.dp)
                            )

                            Text(text = "${UserInfo!!.xp} XP")

                            Divider(
                                modifier = Modifier
                                    .height(15.dp)
                                    .width(2.dp)
                            )

                            Text(text = UserInfo!!.currentBadge)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                ) {
                    for (expertise in UserInfo!!.expertises) {
                        InputChip(
                            selected = false,
                            onClick = {},
                            label = { Text(text = expertise.title) },
                        )
                    }
                }
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )

            TabRow(
                selectedTabIndex = currentTabIndex,
                modifier = Modifier.fillMaxWidth()
            ) {
                tabs.forEachIndexed { index, title -> Tab(
                    selected = currentTabIndex == index,
                    onClick = { currentTabIndex = index },
                    text = { Text(text = title) }
                )}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    UserInfo = User(
        name = "User",
        username = "username",
        email = "user@gmail.com",
        xp = 727,
        currentBadge = "Aprendiz",
        expertises = listOf(Expertise("Matemática"), Expertise("Geografia"), Expertise("Química")),
        token = ""
    )

    MindHubTheme {
        Profile(
            EmptyDestinationsNavigator
        )
    }
}