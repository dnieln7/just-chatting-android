package xyz.dnieln7.composable.button

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import xyz.dnieln7.composable.theme.JCTheme

class JCStatefulButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun given_StatefulButtonStatus_NONE_then_display_none_text() {
        val noneText = "Download"
        val successText = "Downloaded"

        composeTestRule.setContent {
            JCTheme {
                JCStatefulButton(
                    noneText = noneText,
                    successText = successText,
                    statefulButtonStatus = StatefulButtonStatus.NONE,
                    onClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText(noneText).assertIsDisplayed()
    }

    @Test
    fun given_StatefulButtonStatus_LOADING_then_display_progress() {
        val noneText = "Download"
        val successText = "Downloaded"

        composeTestRule.setContent {
            JCTheme {
                JCStatefulButton(
                    noneText = noneText,
                    successText = successText,
                    statefulButtonStatus = StatefulButtonStatus.LOADING,
                    onClick = {},
                )
            }
        }

        composeTestRule.onNodeWithTag("JCStatefulButton_CircularProgressIndicator").assertIsDisplayed()
    }

    @Test
    fun given_StatefulButtonStatus_SUCCESS_then_display_success_text() {
        val noneText = "Download"
        val successText = "Downloaded"

        composeTestRule.setContent {
            JCTheme {
                JCStatefulButton(
                    noneText = noneText,
                    successText = successText,
                    statefulButtonStatus = StatefulButtonStatus.SUCCESS,
                    onClick = {},
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(successText).assertIsDisplayed()
    }
}