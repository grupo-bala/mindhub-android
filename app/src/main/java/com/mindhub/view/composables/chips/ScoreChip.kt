package com.mindhub.view.composables.chips

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ScoreChip(
    score: Int,
    userScore: Int,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit,
) {
    BaseChip {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = null,
            tint = when (userScore) {
                0, -1 -> {
                    Color.White
                }
                else -> {
                    Color.Green
                }
            },
            modifier = Modifier
                .size(16.dp)
                .clickable {
                onIncreaseClick()
            }
        )

        Text(
            text = "$score",
            style = MaterialTheme.typography.labelSmall
        )

        Icon(
            imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = null,
            tint = when (userScore) {
                0, 1 -> {
                    Color.White
                }
                else -> {
                    Color.Red
                }
            },
            modifier = Modifier.size(16.dp).clickable {
                onDecreaseClick()
            }
        )
    }
}