package com.mindhub.view.composables.chips

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CommentsChip(
    commentsQuantity: Int
) {
    BaseChip {
        Icon(imageVector = Icons.Outlined.Email, contentDescription = null)
        Text(text = "$commentsQuantity")
    }
}