package com.mindhub.view.composables.chips

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CommentsChip(
    commentsQuantity: Int
) {
    BaseChip {
        Icon(
            imageVector = Icons.Outlined.Email,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )

        Text(
            text = "$commentsQuantity",
            style = MaterialTheme.typography.labelSmall
        )
    }
}