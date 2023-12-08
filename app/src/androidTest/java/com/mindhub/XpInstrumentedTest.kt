package com.mindhub

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost
import org.junit.Before

class XpInstrumentedTest : InstrumentedTest() {
    @OptIn(ExperimentalTestApi::class)
    @Before
    fun setPostComposable() {
        var navController: NavHostController

        rule.setContent {
            MindHubTheme {
                navController = rememberNavController()
                DestinationsNavHost(navGraph = NavGraphs.root, navController = navController)
            }
        }

        this.login()

        rule.waitUntilAtLeastOneExists(hasText("ou quer responder umas perguntas?"), 1500)

        rule.onNodeWithText("ou quer responder umas perguntas?")
            .performClick()

        rule.onNodeWithText("Pergunta matemática")
            .performClick()
    }
}