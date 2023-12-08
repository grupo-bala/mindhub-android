package com.mindhub.view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.layouts.AppScaffold
import com.mindhub.view.layouts.Views
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
@Composable
fun NavBar(
    currentView: Views,
    navigator: DestinationsNavigator
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.height(52.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            for (view in Views.entries) {
                IconButton(
                    onClick = { navigator.navigate(view.destination) },
                    modifier = Modifier.semantics {
                        contentDescription = "NavBarButton"
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(view.icon),
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
}

@Preview
@Composable
fun NavBarPreview() {
    MindHubTheme {
        AppScaffold(
            currentView = Views.ASK,
            navigator = EmptyDestinationsNavigator
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