package xyz.dnieln7.composable.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.dnieln7.composable.theme.JustChattingTheme

@Composable
fun JustChattingScreenProgressIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(100.dp),
            strokeWidth = 10.dp,
            strokeCap = StrokeCap.Round,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JustChattingScreenProgressIndicatorPreview() {
    JustChattingTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                JustChattingScreenProgressIndicator()
            }
        }
    }
}