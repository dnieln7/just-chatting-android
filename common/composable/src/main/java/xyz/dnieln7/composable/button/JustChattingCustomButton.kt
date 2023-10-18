package xyz.dnieln7.composable.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import xyz.dnieln7.composable.theme.JustChattingTheme

@Composable
fun JustChattingCustomButton(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
    onClick: () -> Unit,
) {
    FilledTonalButton(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        onClick = onClick,
        content = content,
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JustChattingCustomButtonPreview() {
    JustChattingTheme {
        Surface {
            Column {
                JustChattingCustomButton(
                    content = { Text("JustChattingCustomButton") },
                    onClick = {}
                )
            }
        }
    }
}
