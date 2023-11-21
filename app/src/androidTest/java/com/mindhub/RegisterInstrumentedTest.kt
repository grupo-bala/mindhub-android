package com.mindhub

import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.semantics.SemanticsProperties.IsPopup
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.isPopup
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAncestors
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyPress
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.register.Register
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
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
        rule.onNodeWithText("Expertises*")
            .performClick()

        rule.onNodeWithText("Matemática")
            .performClick()

        rule.onNode(isPopup())
            .performClick()
    }

    @Before
    fun setRegisterComposable() {
        rule.setContent {
            MindHubTheme {
                Register(navigator = EmptyDestinationsNavigator)
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
}