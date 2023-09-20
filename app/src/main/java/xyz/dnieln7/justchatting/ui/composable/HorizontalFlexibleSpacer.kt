package xyz.dnieln7.justchatting.ui.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RowScope.HorizontalFlexibleSpacer() {
    Spacer(modifier = Modifier.weight(1F))
}
