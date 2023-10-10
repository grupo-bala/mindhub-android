package com.mindhub.view.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.NavBar
import com.mindhub.view.composables.TopBar

enum class Views(val icon: ImageVector) {
    ASK(Icons.Filled.Email),
    FEED(Icons.Filled.Info),
    MAP(Icons.Filled.Place),
    USER(Icons.Filled.AccountCircle)
}

@Composable
fun AppScaffold(
    currentView: Views,
    topAppBar: @Composable () -> Unit = { TopBar() },
    bottomAppBar: @Composable (Views) -> Unit = { NavBar(currentView) },
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            topAppBar()
        },
        bottomBar = {
            bottomAppBar(currentView)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppScaffoldPreview() {
    MindHubTheme {
        AppScaffold (
            currentView = Views.ASK
        ){
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