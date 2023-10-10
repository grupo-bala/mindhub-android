package com.mindhub.view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.layouts.AppScaffold
import com.mindhub.view.layouts.Views

@Composable
fun NavBar(
    currentView: Views
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxSize()
        ) {
            for (view in Views.entries) {
                Icon(
                    imageVector = view.icon,
                    contentDescription = null,
                    tint = if (view == currentView) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                            .copy(alpha = 0.5f)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun NavBarPreview() {
    MindHubTheme {
        AppScaffold(
            currentView = Views.ASK
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(text = "Scaffold")
            }
        }
    }
}