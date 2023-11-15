package xyz.dnieln7.composable.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import xyz.dnieln7.composable.theme.JCTheme

@Composable
fun JCSlotButton(
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
private fun JCSlotButtonPreview() {
    JCTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                JCSlotButton(
                    content = { Text("JCSlotButtonPreview") },
                    onClick = {}
                )
            }
        }
    }
}
