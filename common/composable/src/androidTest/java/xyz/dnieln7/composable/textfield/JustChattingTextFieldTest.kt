package xyz.dnieln7.composable.textfield

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import xyz.dnieln7.composable.theme.JustChattingTheme

class JustChattingTextFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun given_error_not_null_then_display_error() {
        val error = "Example is not an email"

        composeTestRule.setContent {
            JustChattingTheme {
                JustChattingTextField(
                    value = "example",
                    onValueChange = { },
                    label = "Email",
                    error = error,
                )
            }
        }

        composeTestRule.onNodeWithText(error).assertIsDisplayed()
    }
}