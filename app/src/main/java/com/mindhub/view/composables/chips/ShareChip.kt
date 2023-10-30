package com.mindhub.view.composables.chips

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ShareChip() {
    BaseChip {
        Icon(imageVector = Icons.Outlined.Share, contentDescription = null)
        Text(text = "Compartilhar")
    }
}