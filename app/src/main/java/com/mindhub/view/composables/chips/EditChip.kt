package com.mindhub.view.composables.chips

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditChip(
    navigate: () -> Unit
) {
    BaseChip(
        modifier = Modifier.clickable { navigate() }
    ) {
        Icon(imageVector = Icons.Filled.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
        Text(text = "Editar", style = MaterialTheme.typography.labelSmall)
    }
}