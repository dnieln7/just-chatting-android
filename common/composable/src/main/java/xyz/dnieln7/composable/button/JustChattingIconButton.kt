package xyz.dnieln7.composable.button

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import xyz.dnieln7.composable.theme.JustChattingTheme

@Composable
fun JustChattingIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        content = {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.primary,
            )
        },
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JustChattingIconButtonPreview() {
    JustChattingTheme {
        Surface {
            Column {
                JustChattingIconButton(
                    onClick = {},
                    icon = Icons.Rounded.PlayArrow,
                    contentDescription = "JustChattingButton",
                )
            }
        }
    }
}
