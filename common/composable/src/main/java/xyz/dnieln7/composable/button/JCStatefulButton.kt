package xyz.dnieln7.composable.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.dnieln7.composable.theme.JCTheme

@Composable
fun JCStatefulButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    noneText: String,
    successText: String,
    statefulButtonStatus: StatefulButtonStatus,
    onClick: () -> Unit,
) {
    FilledTonalButton(
        modifier = modifier,
        enabled = enabled,
        shape = MaterialTheme.shapes.small,
        onClick = {
            if (statefulButtonStatus != StatefulButtonStatus.LOADING && statefulButtonStatus != StatefulButtonStatus.SUCCESS) {
                onClick()
            }
        },
        content = {
            when (statefulButtonStatus) {
                StatefulButtonStatus.NONE -> Text(
                    text = noneText
                )

                StatefulButtonStatus.LOADING -> CircularProgressIndicator(
                    modifier = Modifier
                        .size(20.dp)
                        .testTag("JCStatefulButton_CircularProgressIndicator"),
                    strokeWidth = 2.dp,
                    strokeCap = StrokeCap.Round,
                )

                StatefulButtonStatus.SUCCESS -> Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = successText,
                )
            }
        },
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun JCStatefulButtonPreview() {
    JCTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                JCStatefulButton(
                    noneText = "JCStatefulButtonPreview",
                    successText = "JCStatefulButtonPreview",
                    statefulButtonStatus = StatefulButtonStatus.SUCCESS,
                    onClick = {},
                )
            }
        }
    }
}

enum class StatefulButtonStatus {
    NONE,
    LOADING,
    SUCCESS,
}
