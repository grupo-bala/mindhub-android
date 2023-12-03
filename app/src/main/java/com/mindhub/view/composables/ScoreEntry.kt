package com.mindhub.view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import com.mindhub.model.entities.LeaderboardEntry
import com.mindhub.view.destinations.ProfileDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun ScoreEntry(
    position: Int,
    leaderboardEntry: LeaderboardEntry,
    navigator: DestinationsNavigator,
) {
    if (position == 1) {
        Button(
            onClick = {
                navigator.navigate(ProfileDestination(leaderboardEntry.username))
            },
            shape = RoundedCornerShape(5.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = "$position. ${leaderboardEntry.name}")

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(text = "${leaderboardEntry.xp} XP")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.Filled.Star, contentDescription = null)
                }
            }
        }
    } else {
        OutlinedButton(
            onClick = {
                navigator.navigate(ProfileDestination(leaderboardEntry.username))
            },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = "${position}. ${leaderboardEntry.name}")
                Text(text = "${leaderboardEntry.xp} XP")
            }
        }
    }
}