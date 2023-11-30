package com.mindhub.view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Suspended(
    color: Color = MaterialTheme.colorScheme.onPrimary,
    strokeWidth: Dp = 2.dp,
    loadingSize: Dp = 24.dp,
    isLoading: Boolean = false,
    content: @Composable () -> Unit,
) {
    if (isLoading) {
        Row(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                color = color,
                strokeWidth = strokeWidth,
                modifier = Modifier.size(loadingSize)
            )
        }
    } else {
        content()
    }
}