package xyz.dnieln7.justchatting.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import xyz.dnieln7.justchatting.ui.theme.JustChattingTheme

@Composable
fun JustChattingButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    FilledTonalButton(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        onClick = onClick,
        content = { Text(text) }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JustChattingButtonPreview() {
    JustChattingTheme {
        Surface {
            Column {
                JustChattingButton(
                    text = "JustChattingButton",
                    onClick = {}
                )
            }
        }
    }
}
