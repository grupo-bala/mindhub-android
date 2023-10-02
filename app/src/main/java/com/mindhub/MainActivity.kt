package com.mindhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MindHubTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}