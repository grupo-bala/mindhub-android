package com.mindhub

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost
import org.junit.Before
import org.junit.Test

class MaterialInstrumentedTest : InstrumentedTest() {
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
        rule.onAllNodes(hasContentDescription("NavBarButton"))[1]
            .performClick()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun materialFeedShouldShowForYou() {
        rule.waitUntilAtLeastOneExists(hasText("Material matemática"), 1000)
        rule.waitUntilAtLeastOneExists(hasText("Teste"), 1000)
        rule.waitUntilAtLeastOneExists(hasText("0"), 1000)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun materialFeedShouldShowRecents() {
        rule.onNodeWithText("Mais recentes")
            .performClick()

        rule.waitUntilAtLeastOneExists(hasText("Material português"), 1000)
        rule.waitUntilAtLeastOneExists(hasText("Teste"), 1000)
        rule.waitUntilAtLeastOneExists(hasText("0"), 1000)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun addMaterialShouldNavigateToAskPage() {
        rule.onNodeWithContentDescription("AddPostButton")
            .performClick()

        rule.onNodeWithText("Título*")
            .performClick()
            .performTextInput("Novo material")

        rule.onNodeWithText("Categoria*")
            .performClick()

        rule.onNodeWithText("Matemática")
            .performClick()

        rule.onNodeWithText("Descrição*")
            .performClick()
            .performTextInput("Descrição teste")

        rule.onNodeWithText("Publicar")
            .performClick()

        rule.waitUntilAtLeastOneExists(hasText("Novo material"), 1500)
        rule.waitUntilAtLeastOneExists(hasText("Matemática"), 1500)
        rule.waitUntilAtLeastOneExists(hasText("Descrição teste"), 1500)
    }
}