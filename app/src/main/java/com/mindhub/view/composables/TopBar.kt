package com.mindhub.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindhub.common.services.StoreData
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.NavGraphs
import com.mindhub.view.destinations.LoginDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navigator: DestinationsNavigator,
    hasBackArrow: Boolean = false,
    hasLogout: Boolean = false,
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "MindHub",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                )
            },
            navigationIcon = if (hasBackArrow) {
                { IconButton(onClick = { navigator.popBackStack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                } }
            } else {
                {}
            },
            actions = if (hasLogout) {
                {
                    IconButton(onClick = {
                        scope.launch {
                            val storeData = StoreData(context)
                            storeData.saveData("", "")
                            navigator.popBackStack(NavGraphs.root, false, false)
                            navigator.navigate(LoginDestination)
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = null)
                    }
                }
            } else {
                {}
            }
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Black)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    MindHubTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column {
                TopBar(EmptyDestinationsNavigator)
            }
        }
    }
}