package xyz.dnieln7.composable.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import xyz.dnieln7.composable.theme.JCTheme

@Composable
fun JCProgressIndicator(modifier: Modifier = Modifier, size: Dp = 100.dp, strokeWidth: Dp = 10.dp) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(size),
            strokeWidth = strokeWidth,
            strokeCap = StrokeCap.Round,
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.secondaryContainer,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun JCProgressIndicatorPreview() {
    JCTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                JCProgressIndicator()
            }
        }
    }
}
