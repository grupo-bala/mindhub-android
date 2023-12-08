package com.mindhub

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.NavGraphs
import com.mindhub.view.login.Login
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginInstrumentedTest {
    @get:Rule
    val rule = createComposeRule()

    private fun typeUserInfo(email: String, password: String) {
        rule.onNodeWithText("Email")
            .performTextInput(email)

        rule.onNodeWithText("Senha")
            .performTextInput(password)
    }

    @Before
    fun setLoginComposable() {
        var navController: NavHostController

        rule.setContent {
            MindHubTheme {
                navController = rememberNavController()
                DestinationsNavHost(navGraph = NavGraphs.root, navController = navController)
            }
        }
    }
    @Test
    fun emptyTextFieldsShouldDisableLoginButton() {
        rule.onNodeWithText("Entrar")
            .assertIsNotEnabled()
    }

    @Test
    fun filledTextFieldsShowEnabledLoginButton() {
        typeUserInfo("teste", "teste")

        rule.onNodeWithText("Entrar")
            .assertIsEnabled()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun wrongCredentialsShouldShowsErrorMessage() {
        typeUserInfo("teste", "wrong")

        rule.onNodeWithText("Entrar")
            .performClick()

        rule.waitUntilExactlyOneExists(
            hasTextExactly("Email ou senha inválidos"),
            1000
        )
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun rightCredentialsShouldNavigateToHomePage() {
        typeUserInfo("teste", "teste")

        rule.onNodeWithText("Entrar")
            .performClick()

        rule.waitUntilExactlyOneExists(
            hasTextExactly("O que você precisa saber?"),
            1000
        )
    }
}