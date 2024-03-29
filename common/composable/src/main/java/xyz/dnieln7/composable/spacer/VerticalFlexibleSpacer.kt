package xyz.dnieln7.composable.spacer

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ColumnScope.VerticalFlexibleSpacer() {
    Spacer(modifier = Modifier.weight(1F))
}
