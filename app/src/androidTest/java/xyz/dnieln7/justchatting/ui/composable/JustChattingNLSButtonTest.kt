package xyz.dnieln7.justchatting.ui.composable

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import xyz.dnieln7.justchatting.ui.theme.JustChattingTheme

class JustChattingNLSButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun given_NLSButtonStatus_NONE_then_display_none_text() {
        val noneText = "Download"
        val successText = "Downloaded"

        composeTestRule.setContent {
            JustChattingTheme {
                JustChattingNLSButton(
                    noneText = noneText,
                    successText = successText,
                    nlsButtonStatus = NLSButtonStatus.NONE,
                    onClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText(noneText).assertIsDisplayed()
    }

    @Test
    fun given_NLSButtonStatus_LOADING_then_display_progress() {
        val noneText = "Download"
        val successText = "Downloaded"

        composeTestRule.setContent {
            JustChattingTheme {
                JustChattingNLSButton(
                    noneText = noneText,
                    successText = successText,
                    nlsButtonStatus = NLSButtonStatus.LOADING,
                    onClick = {},
                )
            }
        }

        composeTestRule.onNodeWithTag(JC_NLS_BUTTON_L_TAG).assertIsDisplayed()
    }

    @Test
    fun given_NLSButtonStatus_SUCCESS_then_display_success_text() {
        val noneText = "Download"
        val successText = "Downloaded"

        composeTestRule.setContent {
            JustChattingTheme {
                JustChattingNLSButton(
                    noneText = noneText,
                    successText = successText,
                    nlsButtonStatus = NLSButtonStatus.SUCCESS,
                    onClick = {},
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(successText).assertIsDisplayed()
    }
}