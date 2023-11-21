package com.mindhub

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.login.Login
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
        rule.setContent {
            MindHubTheme {
                Login(navigator = EmptyDestinationsNavigator)
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

    @Test
    fun wrongCredentialsShouldShowsErrorMessage() {
        typeUserInfo("teste", "teste")

        rule.onNodeWithText("Entrar")
            .performClick()

        rule.onNodeWithText("Email ou senha inválidos")
            .assertExists()
    }
}