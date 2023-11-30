package com.mindhub.view.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
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
import com.mindhub.view.composables.Suspended
import com.mindhub.view.destinations.ProfileDestination
import com.mindhub.view.layouts.AppScaffold
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.view.layouts.Views
import com.mindhub.viewmodel.profile.RankingViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination
@Composable
fun Ranking(
    navigator: DestinationsNavigator
) {
    val rankingViewModel: RankingViewModel = viewModel()

    rankingViewModel.loadLeaderBoard()

    AppScaffold(
        currentView = Views.USER,
        navigator = navigator,
        hasBackArrow = true,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
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

                Spacer(modifier = Modifier.height(16.dp))

                Suspended(
                    isLoading = rankingViewModel.isLoading
                ) {
                    Text(text = "${rankingViewModel.userPositionInRank}º lugar")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Suspended(
                isLoading = rankingViewModel.isLoading
            ) {
                if (rankingViewModel.hasLoaded) {
                    Button(
                        onClick = {
                            navigator.navigate(ProfileDestination(rankingViewModel.leaderboardEntries[0].username))
                        },
                        shape = RoundedCornerShape(5.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(text = "1. ${rankingViewModel.leaderboardEntries[0].name}")

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(text = "${rankingViewModel.leaderboardEntries[0].xp} XP")
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(imageVector = Icons.Filled.Star, contentDescription = null)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    SpacedColumn(
                        spacing = 8,
                        verticalAlignment = Alignment.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        rankingViewModel.leaderboardEntries.forEachIndexed { index, e ->
                            if (index != 0) {
                                OutlinedButton(
                                    onClick = {
                                        navigator.navigate(ProfileDestination(e.username))
                                    },
                                    shape = RoundedCornerShape(5.dp),
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        Text(text = "${index + 1}. ${e.name}")
                                        Text(text = "${e.xp} XP")
                                    }
                                }
                            }
                        }
                    }
                }


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RankingPreview() {
    CurrentUser.user = User(
        name = "Administrador",
        email = "admin@admin.com",
        username = "admin",
        xp = 0,
        currentBadge = Badge("Aprendiz", 0, 0),
        badges = listOf(),
        expertises = listOf(
            Expertise("Matemática"),
            Expertise("Física"),
            Expertise("Inglês")
        )
    )

    MindHubTheme {
        Ranking(
            EmptyDestinationsNavigator
        )
    }
}