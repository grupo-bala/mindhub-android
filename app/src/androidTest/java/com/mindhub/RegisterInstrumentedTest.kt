package com.mindhub

import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.semantics.SemanticsProperties.IsPopup
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.isPopup
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onAncestors
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyPress
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.NavGraphs
import com.mindhub.view.destinations.RegisterDestination
import com.mindhub.view.register.Register
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterInstrumentedTest {
    @get:Rule
    val rule = createComposeRule()

    private fun typeUserInfo(
        name: String,
        email: String,
        username: String,
        password: String,
        passwordConfirmation: String
    ) {
        rule.onNodeWithText("Nome*")
            .performTextInput(name)

        rule.onNodeWithText("Email*")
            .performTextInput(email)

        rule.onNodeWithText("Nome de usuário*")
            .performTextInput(username)

        rule.onNodeWithText("Senha*")
            .performTextInput(password)

        rule.onNodeWithText("Confirmar senha*")
            .performTextInput(passwordConfirmation)

        selectExpertises()
    }

    private fun selectExpertises() {
        rule.onAllNodesWithText("Expertises*")
            .onFirst()
            .performClick()

        rule.onAllNodesWithText("Matemática")
            .onFirst()
            .performClick()

        rule.onAllNodes(isPopup())
            .onFirst()
            .performClick()
    }

    @Before
    fun setRegisterComposable() {
        var navController: NavHostController

        rule.setContent {
            MindHubTheme {
                navController = rememberNavController()
                DestinationsNavHost(navGraph = NavGraphs.root, navController = navController)
                navController.navigate(RegisterDestination)
            }
        }
    }

    @Test
    fun emptyTextFieldsShouldDisableRegisterButton() {
        rule.onNodeWithText("Confirmar")
            .assertIsNotEnabled()
    }

    @Test
    fun filledTextFieldsShouldEnableRegisterButton() {
        typeUserInfo(
            name = "teste",
            username = "teste",
            email = "teste",
            password = "123",
            passwordConfirmation = "321"
        )

        rule.onNodeWithText("Confirmar")
            .assertIsEnabled()
    }

    @Test
    fun differentPasswordsShouldShowsErrorMessage() {
        typeUserInfo(
            name = "teste",
            username = "teste",
            email = "teste",
            password = "123",
            passwordConfirmation = "321"
        )

        rule.onNodeWithText("Confirmar")
            .performClick()

        rule.onNodeWithText("As senhas não coincidem")
            .assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun emailAlreadyExistsShouldShowsErrorMessage() {
        typeUserInfo(
            name = "teste",
            username = "teste2",
            email = "teste",
            password = "123",
            passwordConfirmation = "123"
        )

        rule.onNodeWithText("Confirmar")
            .performClick()

        rule.waitUntilExactlyOneExists(
            hasTextExactly("Esse email já está em uso"),
            1000
        )
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun usernameAlreadyExistsShouldShowsErrorMessage() {
        typeUserInfo(
            name = "teste",
            username = "teste",
            email = "teste2",
            password = "123",
            passwordConfirmation = "123"
        )

        rule.onNodeWithText("Confirmar")
            .performClick()

        rule.waitUntilExactlyOneExists(
            hasTextExactly("Esse nome de usuário já está em uso"),
            1000
        )
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun correctFieldsShouldNavigateToHomePage() {
        typeUserInfo(
            name = "teste2",
            username = "teste2",
            email = "teste2",
            password = "123",
            passwordConfirmation = "123"
        )

        rule.onNodeWithText("Confirmar")
            .performClick()

        rule.waitUntilExactlyOneExists(
            hasTextExactly("O que você precisa saber?"),
            1000
        )
    }
}