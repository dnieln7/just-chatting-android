package xyz.dnieln7.justchatting.ui.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun RowScope.HorizontalSpacer(of: Dp) {
    Spacer(modifier = Modifier.width(of))
}
