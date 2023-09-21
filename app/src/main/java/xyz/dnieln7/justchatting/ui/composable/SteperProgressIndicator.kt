package xyz.dnieln7.justchatting.ui.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.dnieln7.justchatting.ui.theme.JustChattingTheme

@Composable
fun StepperProgressIndicator(modifier: Modifier = Modifier, currentSteps: Int, totalSteps: Int) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        for (i in 1..totalSteps) {
            val color = if (i <= currentSteps) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.secondaryContainer
            }

            Surface(
                modifier = Modifier
                    .height(8.dp)
                    .weight(1F),
                color = color,
                shape = RoundedCornerShape(2.dp),
                content = {},
            )

            if (i != totalSteps) {
                HorizontalSpacer(20.dp)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StepperProgressIndicatorPreview() {
    JustChattingTheme(darkTheme = false) {
        Surface {
            StepperProgressIndicator(
                modifier = Modifier.padding(20.dp),
                currentSteps = 1,
                totalSteps = 3
            )
        }
    }
}