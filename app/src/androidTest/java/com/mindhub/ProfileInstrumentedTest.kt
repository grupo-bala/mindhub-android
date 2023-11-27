package com.mindhub

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.NavGraphs
import com.mindhub.view.destinations.AskHomeDestination
import com.mindhub.view.destinations.RegisterDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AskInstrumentedTest {
    @get:Rule
    val rule = createComposeRule()

    @Before
    fun setAskComposable() {
        var navController: NavHostController

        rule.setContent {
            MindHubTheme {
                navController = rememberNavController()
                DestinationsNavHost(navGraph = NavGraphs.root, navController = navController)
                navController.navigate(AskHomeDestination)
            }
        }
    }

    @Test
    fun 
}