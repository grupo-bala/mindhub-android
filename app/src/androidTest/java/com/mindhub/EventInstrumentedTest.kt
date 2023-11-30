package com.mindhub

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost
import org.junit.Before
import org.junit.Test

class EventInstrumentedTest : InstrumentedTest() {
    @OptIn(ExperimentalTestApi::class)
    @Before
    fun setMaterialComposable() {
        rule.setContent {
            MindHubTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }

        this.login()

        rule.waitUntilAtLeastOneExists(hasContentDescription("NavBarButton"), 1000)
        rule.onAllNodes(hasContentDescription("NavBarButton"))[2]
            .performClick()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun eventFeedShouldShowForYouAndRecentsEquals() {
        rule.waitUntilAtLeastOneExists(hasText("Evento teste"), 1000)
        rule.waitUntilAtLeastOneExists(hasText("Teste"), 1000)
        rule.waitUntilAtLeastOneExists(hasText("0"), 1000)
        rule.waitUntilAtLeastOneExists(hasText("Local teste"))
        rule.waitUntilAtLeastOneExists(hasText("01/01/1970"))

        rule.onNodeWithText("Mais recentes")
            .performClick()

        rule.waitUntilAtLeastOneExists(hasText("Evento teste"), 1000)
        rule.waitUntilAtLeastOneExists(hasText("Teste"), 1000)
        rule.waitUntilAtLeastOneExists(hasText("0"), 1000)
        rule.waitUntilAtLeastOneExists(hasText("Local teste"))
        rule.waitUntilAtLeastOneExists(hasText("01/01/1970"))
    }
}