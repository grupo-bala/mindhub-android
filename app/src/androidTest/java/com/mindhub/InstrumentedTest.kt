package com.mindhub

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule

open class InstrumentedTest {
    @get:Rule
    val rule = createComposeRule()

    fun login() {
        rule.onNodeWithText("Email")
            .performTextInput("teste")

        rule.onNodeWithText("Senha")
            .performTextInput("teste")

        rule.onNodeWithText("Entrar")
            .performClick()
    }
}