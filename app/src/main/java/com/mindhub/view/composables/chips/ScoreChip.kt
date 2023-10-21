package com.mindhub.view.composables.chips

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ScoreChip(
    score: Int
) {
    BaseChip {
        Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = null)
        Text(text = "$score")
        Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = null)
    }
}