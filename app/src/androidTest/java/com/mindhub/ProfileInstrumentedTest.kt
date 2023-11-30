package com.mindhub

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mindhub.common.services.CurrentUser
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.NavGraphs
import com.mindhub.view.destinations.ProfileDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileInstrumentedTest : InstrumentedTest() {
    @OptIn(ExperimentalTestApi::class)
    @Before
    fun setProfileComposable() {
        var navController: NavHostController

        rule.setContent {
            MindHubTheme {
                navController = rememberNavController()
                DestinationsNavHost(navGraph = NavGraphs.root, navController = navController)
            }
        }

        this.login()

        rule.waitUntilAtLeastOneExists(hasContentDescription("NavBarButton"), 1000)
        rule.onAllNodes(hasContentDescription("NavBarButton"))
            .onLast()
            .performClick()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun existentUserShouldShowInfo() {
        rule.waitUntilAtLeastOneExists(hasText(CurrentUser.user!!.name), 1000)
        rule.waitUntilAtLeastOneExists(hasTextExactly("0 XP"), 1000)
        rule.waitUntilAtLeastOneExists(hasTextExactly("Aprendiz"), 1000)
        rule.waitUntilAtLeastOneExists(hasTextExactly("Matemática"), 1000)

        rule.onNodeWithText("Perguntas")
            .performClick()

        rule.waitUntilAtLeastOneExists(hasText("Pergunta matemática"), 1000)

        rule.onNodeWithText("Materiais")
            .performClick()

        rule.waitUntilAtLeastOneExists(hasText("Material matemática"), 1000)

        rule.onNodeWithText("Eventos")
            .performClick()

        rule.waitUntilAtLeastOneExists(hasText("Evento teste"), 1000)
    }
}