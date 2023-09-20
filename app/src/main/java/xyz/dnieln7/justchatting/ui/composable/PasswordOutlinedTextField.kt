package xyz.dnieln7.justchatting.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import xyz.dnieln7.justchatting.ui.theme.JustChattingTheme

@Composable
fun PasswordOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onDone: (KeyboardActionScope.() -> Unit)? = null,
    onGo: (KeyboardActionScope.() -> Unit)? = null,
    onNext: (KeyboardActionScope.() -> Unit)? = null,
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }

    val imeAction = when {
        onDone != null -> ImeAction.Done
        onGo != null -> ImeAction.Go
        onNext != null -> ImeAction.Next
        else -> ImeAction.None
    }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text("Password") },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable { showPassword = !showPassword },
                imageVector = if (showPassword) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                contentDescription = if (showPassword) "Hide password" else "Show password"
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction,
        ),
        keyboardActions = KeyboardActions(
            onDone = onDone,
            onGo = onGo,
            onNext = onNext,
        ),
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        shape = MaterialTheme.shapes.small,
    )
}

@Preview(showBackground = true)
@Composable
fun OutlinedTextFieldPreview() {
    JustChattingTheme {
        PasswordOutlinedTextField(value = "password", onValueChange = {})
    }
}
