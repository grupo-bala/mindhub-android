package com.mindhub.view.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SpacedColumn(
    spacing: Int,
    verticalAlignment: Alignment.Vertical,
    horizontalAlignment: Alignment.Horizontal,
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit)
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(spacing.dp, verticalAlignment),
        horizontalAlignment = horizontalAlignment,
        modifier = modifier,
        content = content
    )
}
