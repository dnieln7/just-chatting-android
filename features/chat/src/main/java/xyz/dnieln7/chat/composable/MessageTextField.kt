package xyz.dnieln7.chat.composable

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.dnieln7.chat.R
import xyz.dnieln7.composable.button.JCIconButton
import xyz.dnieln7.composable.spacer.HorizontalSpacer
import xyz.dnieln7.composable.theme.JCTheme

@Composable
fun MessageTextField(
    enabled: Boolean = true,
    onSend: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    var message by rememberSaveable { mutableStateOf("") }

    val label = if (enabled) {
        stringResource(R.string.start_typing)
    } else {
        stringResource(R.string.please_wait)
    }

    ElevatedCard(
        shape = CutCornerShape(0.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1F),
                enabled = enabled,
                value = message,
                onValueChange = { message = it },
                label = {
                    if (!isFocused) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Send,
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        val trimmedMessage = message.trim()

                        if (trimmedMessage.isNotEmpty()) {
                            onSend(trimmedMessage)
                            message = ""
                        }

                        focusManager.clearFocus()
                    }
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                ),
                interactionSource = interactionSource,
            )
            HorizontalSpacer(of = 8.dp)
            JCIconButton(
                icon = Icons.Rounded.Send,
                iconTint = if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                contentDescription = stringResource(R.string.send_message),
                onClick = {
                    val trimmedMessage = message.trim()

                    if (trimmedMessage.isNotEmpty()) {
                        onSend(trimmedMessage)
                        message = ""
                    }

                    focusManager.clearFocus()
                },
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MessageTextFieldPreview() {
    JCTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                MessageTextField(
                    enabled = false,
                    onSend = {},
                )
            }
        }
    }
}
