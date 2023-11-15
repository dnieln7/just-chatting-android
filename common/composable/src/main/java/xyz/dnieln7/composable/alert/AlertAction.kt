package xyz.dnieln7.composable.alert

data class AlertAction(
    val text: String,
    val onClick: () -> Unit,
)
