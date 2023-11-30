package com.mindhub

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost
import okhttp3.internal.wait
import org.junit.FixMethodOrder
import org.junit.Before
import org.junit.Test
import org.junit.runner.OrderWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CommentInstrumentedTest : InstrumentedTest() {
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

        rule.waitUntilAtLeastOneExists(hasContentDescription("AddCommentButton"), 1500)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun addCommentInPostShouldShowComment() {
        rule.onNodeWithContentDescription("AddCommentButton")
            .performClick()

        rule.onNodeWithText("Comentário")
            .performClick()
            .performTextInput("Comentario teste")

        rule.onNodeWithContentDescription("ConfirmAddCommentButton")
            .performClick()

        rule.waitUntilAtLeastOneExists(hasText("Comentario teste"), 10000)
        rule.waitUntilAtLeastOneExists(hasText("1 comentários"))
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun removeCommentShouldList() {
        rule.onAllNodesWithContentDescription("DeleteCommentButton")[0]
            .performClick()

        rule.onNodeWithText("Confirmar")
            .performClick()

        rule.onNodeWithText("0 comentários")
            .assertExists()
    }
}