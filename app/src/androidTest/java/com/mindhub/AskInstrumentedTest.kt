package com.mindhub

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.mindhub.common.services.CurrentUser
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost
import org.junit.Before
import org.junit.Test

class AskInstrumentedTest : InstrumentedTest() {
    @Before
    fun setAskComposable() {
        rule.setContent { 
            MindHubTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }

        this.login()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun askHomeShouldHaveGreetings() {
        rule.waitUntilAtLeastOneExists(hasText("Olá, ${CurrentUser.user!!.name}!"), 1500)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun askFeedShouldShowForYou() {
        rule.waitUntilAtLeastOneExists(hasText("ou quer responder umas perguntas?"), 1500)

        rule.onNodeWithText("ou quer responder umas perguntas?")
            .performClick()

        rule.waitUntilAtLeastOneExists(hasText("Pergunta matemática"), 1500)
        rule.waitUntilAtLeastOneExists(hasText("Teste"), 1500)
        rule.waitUntilAtLeastOneExists(hasText("0"), 1500)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun askFeedShouldShowRecents() {
        rule.waitUntilAtLeastOneExists(hasText("ou quer responder umas perguntas?"), 1500)

        rule.onNodeWithText("ou quer responder umas perguntas?")
            .performClick()

        rule.onNodeWithText("Mais recentes")
            .performClick()

        rule.waitUntilAtLeastOneExists(hasText("Pergunta português"), 1500)
        rule.waitUntilAtLeastOneExists(hasText("Teste"), 1500)
        rule.waitUntilAtLeastOneExists(hasText("0"), 1500)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun searchForAskShouldReturnTwoResults() {
        rule.waitUntilAtLeastOneExists(hasText("Digite a sua dúvida"), 1500)

        rule.onNodeWithText("Digite a sua dúvida")
            .performClick()

        rule.onNodeWithText("Digite a sua dúvida")
            .performClick()
            .performTextInput("Pergunta")

        rule.waitUntilAtLeastOneExists(hasText("Pergunta matemática"), 1500)
        rule.waitUntilAtLeastOneExists(hasText("Pergunta português"), 1500)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun searchForAskShouldReturnOneResult() {
        rule.waitUntilAtLeastOneExists(hasText("Digite a sua dúvida"), 1500)

        rule.onNodeWithText("Digite a sua dúvida")
            .performClick()

        rule.onNodeWithText("Digite a sua dúvida")
            .performClick()
            .performTextInput("mat")

        rule.waitUntilAtLeastOneExists(hasText("Pergunta matemática"), 1500)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun addAskShouldNavigateToAskPage() {
        rule.waitUntilAtLeastOneExists(hasText("Digite a sua dúvida"), 1500)

        rule.onNodeWithText("Digite a sua dúvida")
            .performClick()

        rule.onNodeWithText("Digite a sua dúvida")
            .performClick()
            .performTextInput("Nova pergunta")

        rule.waitUntilAtLeastOneExists(hasContentDescription("AddAskButton"), 1500)

        rule.onNodeWithContentDescription("AddAskButton")
            .performClick()

        rule.onNodeWithText("Categoria*")
            .performClick()

        rule.onNodeWithText("Matemática")
            .performClick()

        rule.onNodeWithText("Descrição*")
            .performClick()
            .performTextInput("Descrição teste")

        rule.onNodeWithText("Publicar")
            .performClick()

        rule.waitUntilAtLeastOneExists(hasText("Nova pergunta"), 1500)
        rule.waitUntilAtLeastOneExists(hasText("Matemática"), 1500)
        rule.waitUntilAtLeastOneExists(hasText("Descrição teste"), 1500)
    }
}