package xyz.dnieln7.composable.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.dnieln7.composable.R
import xyz.dnieln7.composable.button.JustChattingButton
import xyz.dnieln7.composable.extension.isPortrait
import xyz.dnieln7.composable.spacer.VerticalSpacer
import xyz.dnieln7.composable.theme.JustChattingTheme

@Composable
fun JustChattingErrorWithRetry(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    error: String,
    onRetry: () -> Unit,
) {
    val isPortrait = LocalConfiguration.current.isPortrait()

    if (isPortrait) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier.size(100.dp),
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = error,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.error
                ),
            )
            VerticalSpacer(of = 24.dp)
            JustChattingButton(
                text = stringResource(R.string.retry),
                onClick = onRetry,
            )
        }
    } else {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier.weight(1F),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    modifier = Modifier.size(150.dp),
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            Column(
                modifier = Modifier.weight(1F),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = error,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.error
                    ),
                )
                VerticalSpacer(of = 24.dp)
                JustChattingButton(
                    text = stringResource(R.string.retry),
                    onClick = onRetry,
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JustChattingErrorWithRetryPreview() {
    JustChattingTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                JustChattingErrorWithRetry(
                    modifier = Modifier.fillMaxSize(),
                    icon = Icons.Rounded.Warning,
                    error = "There was an internal error",
                    onRetry = {}
                )
            }
        }
    }
}