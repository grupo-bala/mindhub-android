package com.mindhub.view.composables.chips

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScoreChip(
    score: Int,
    userScore: Int,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit,
) {
    BaseChip {
        Icon(
            imageVector = Icons.Filled.ThumbUp,
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
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )

        Icon(
            imageVector = Icons.Filled.ThumbUp,
            contentDescription = null,
            tint = when (userScore) {
                0, 1 -> {
                    Color.White
                }
                else -> {
                    Color.Red
                }
            },
            modifier = Modifier
                .size(16.dp)
                .rotate(180f)
                .clickable {
                onDecreaseClick()
            }
        )
    }
}