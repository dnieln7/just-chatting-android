package xyz.dnieln7.composable.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import xyz.dnieln7.composable.R
import xyz.dnieln7.composable.theme.JustChattingTheme

@Composable
fun JustChattingPasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String = stringResource(R.string.password),
    error: String? = null,
    passwordAction: PasswordAction? = null,
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }

    val imeAction = when {
        passwordAction?.imeAction != null -> passwordAction.imeAction
        else -> ImeAction.None
    }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = error != null,
        supportingText = if (error != null) ({ Text(error) }) else null,
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable { showPassword = !showPassword },
                imageVector = if (showPassword) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                contentDescription = if (showPassword) stringResource(R.string.hide_password)
                else stringResource(R.string.show_password)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction,
        ),
        keyboardActions = passwordAction?.buildKeyboardActions() ?: KeyboardActions.Default,
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        shape = MaterialTheme.shapes.small,
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OutlinedTextFieldPreview() {
    JustChattingTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                JustChattingPasswordTextField(value = "password", onValueChange = {})
            }
        }
    }
}

data class PasswordAction(
    val imeAction: ImeAction,
    val action: KeyboardActionScope.() -> Unit,
) {
    fun buildKeyboardActions(): KeyboardActions {
        return when (imeAction) {
            ImeAction.Go -> {
                KeyboardActions(onGo = action)
            }

            ImeAction.Search -> {
                KeyboardActions(onSearch = action)
            }

            ImeAction.Send -> {
                KeyboardActions(onSend = action)
            }

            ImeAction.Previous -> {
                KeyboardActions(onPrevious = action)
            }

            ImeAction.Next -> {
                KeyboardActions(onNext = action)
            }

            ImeAction.Done -> {
                KeyboardActions(onDone = action)
            }

            else -> {
                KeyboardActions()
            }
        }
    }
}
