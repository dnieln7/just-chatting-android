package xyz.dnieln7.justchatting.ui.composable

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun ColumnScope.VerticalSpacer(of: Dp) {
    Spacer(modifier = Modifier.height(of))
}
