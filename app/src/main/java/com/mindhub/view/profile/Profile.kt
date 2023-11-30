package com.mindhub.view.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import com.mindhub.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mindhub.BuildConfig
import com.mindhub.common.services.CurrentUser
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.User
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.MeasureViewWidth
import com.mindhub.view.composables.Suspended
import com.mindhub.view.composables.Tabs
import com.mindhub.view.composables.post.PostList
import com.mindhub.view.destinations.AskViewDestination
import com.mindhub.view.destinations.EditProfileDestination
import com.mindhub.view.destinations.EventViewDestination
import com.mindhub.view.destinations.MaterialViewDestination
import com.mindhub.view.destinations.RankingDestination
import com.mindhub.view.layouts.AppScaffold
import com.mindhub.view.layouts.Views
import com.mindhub.viewmodel.profile.ProfileViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun Profile(
    navigator: DestinationsNavigator,
    username: String? = null
) {
    val profileViewModel: ProfileViewModel = viewModel()

    val tabs = listOf("Perguntas", "Materiais", "Eventos")

    LaunchedEffect(key1 = true) {
        profileViewModel.loadProfile(username ?: CurrentUser.user!!.username)
    }

    AppScaffold(
        currentView = Views.USER,
        navigator = navigator,
        hasLogout = username == null,
        hasBackArrow = username != null,
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
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
                            .data(
                                "${BuildConfig.apiPrefix}/static/user/${CurrentUser.user!!.username}"
                            )
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .width(128.dp)
                            .height(128.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

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
                                Text(text = profileViewModel.username)
                            }) { width ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = profileViewModel.username)

                                    if (username == null) {
                                        IconButton(
                                            onClick = { navigator.navigate(EditProfileDestination) },
                                            modifier = Modifier
                                                .padding(start = width + 32.dp)
                                                .height(20.dp)
                                        ) {
                                            Icon(imageVector = Icons.Default.Create, contentDescription = null)
                                        }
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterHorizontally)
                            ) {
                                Text(text = "@${profileViewModel.username}")

                                Divider(
                                    modifier = Modifier
                                        .height(15.dp)
                                        .width(2.dp)
                                )

                                Text(text = "${profileViewModel.xp} XP")

                                Divider(
                                    modifier = Modifier
                                        .height(15.dp)
                                        .width(2.dp)
                                )

                                Text(text = profileViewModel.badge.title)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Suspended(
                        isLoading = profileViewModel.isLoading
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                        ) {
                            for (expertise in profileViewModel.expertises) {
                                InputChip(
                                    selected = false,
                                    onClick = {},
                                    label = { Text(text = expertise.title) },
                                )
                            }
                        }
                    }
                }

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                )

                Tabs(
                    tabs = tabs,
                    tabsContent = listOf({
                        Suspended(isLoading = profileViewModel.isLoading) {
                            PostList(
                                posts = profileViewModel.askPosts,
                                modifier = Modifier.padding(horizontal = 16.dp),
                                onClick = { navigator.navigate(AskViewDestination(it.id)) }
                            )
                        }
                    }, {
                        Suspended(isLoading = profileViewModel.isLoading) {
                            PostList(
                                posts = profileViewModel.materialPosts,
                                modifier = Modifier.padding(horizontal = 16.dp),
                                onClick = { navigator.navigate(MaterialViewDestination(it.id)) }
                            )
                        }
                    }, {
                        Suspended(isLoading = profileViewModel.isLoading) {
                            PostList(
                                posts = profileViewModel.eventsPosts,
                                modifier = Modifier.padding(horizontal = 16.dp),
                                onClick = { navigator.navigate(EventViewDestination(it.id)) }
                            )
                        }
                    })
                )
            }

            if (username == null) {
                FloatingActionButton(
                    onClick = {
                        navigator.navigate(RankingDestination)
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.podium),
                        contentDescription = null,
                        modifier = Modifier
                            .height(32.dp)
                            .width(32.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
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
        Profile(
            EmptyDestinationsNavigator
        )
    }
}