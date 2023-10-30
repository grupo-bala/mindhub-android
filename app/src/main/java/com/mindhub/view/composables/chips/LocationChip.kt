package com.mindhub.view.composables.chips

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LocationChip() {
    BaseChip {
        Icon(imageVector = Icons.Filled.Place, contentDescription = null)
        Text(text = "Localização")
    }
}