package xyz.dnieln7.justchatting.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.dnieln7.justchatting.ui.theme.JustChattingTheme

@Composable
fun JustChattingNLSButton(
    modifier: Modifier = Modifier,
    noneText: String,
    successText: String? = null,
    nlsButtonStatus: NLSButtonStatus,
    onClick: () -> Unit,
) {
    FilledTonalButton(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        onClick = {
            if (nlsButtonStatus != NLSButtonStatus.LOADING && nlsButtonStatus != NLSButtonStatus.SUCCESS) {
                onClick()
            }
        },
        content = {
            when (nlsButtonStatus) {
                NLSButtonStatus.NONE -> Text(
                    text = noneText
                )

                NLSButtonStatus.LOADING -> CircularProgressIndicator(
                    modifier = Modifier
                        .size(20.dp)
                        .testTag(JC_NLS_BUTTON_L_TAG),
                    strokeWidth = 2.dp
                )

                NLSButtonStatus.SUCCESS -> Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = successText,
                )
            }
        },
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JustChattingNLSButtonPreview() {
    JustChattingTheme {
        Surface {
            Column {
                JustChattingNLSButton(
                    noneText = "JustChattingNLSButton",
                    successText = "Success",
                    nlsButtonStatus = NLSButtonStatus.SUCCESS,
                    onClick = {},
                )
            }
        }
    }
}

enum class NLSButtonStatus {
    NONE,
    LOADING,
    SUCCESS,
}

const val JC_NLS_BUTTON_L_TAG = "JC_NLS_BUTTON_L_TAG"
