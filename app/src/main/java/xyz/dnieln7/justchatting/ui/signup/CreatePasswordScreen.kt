package xyz.dnieln7.justchatting.ui.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import xyz.dnieln7.justchatting.R
import xyz.dnieln7.justchatting.domain.validation.PasswordsValidationError
import xyz.dnieln7.justchatting.framework.extensions.isPortrait
import xyz.dnieln7.justchatting.ui.composable.JustChattingButton
import xyz.dnieln7.justchatting.ui.composable.JustChattingPasswordTextField
import xyz.dnieln7.justchatting.ui.composable.PasswordAction
import xyz.dnieln7.justchatting.ui.composable.StepperProgressIndicator
import xyz.dnieln7.justchatting.ui.composable.VerticalSpacer

@Composable
fun CreatePasswordScreen(
    uiState: SignupState.CreatePassword,
    createPassword: (password: String, password2: String) -> Unit,
) {
    val isPortrait = LocalConfiguration.current.isPortrait()
    val focusManager = LocalFocusManager.current

    var password by rememberSaveable { mutableStateOf("") }
    var password2 by rememberSaveable { mutableStateOf("") }

    val paddingMultiplier = if (isPortrait) 4 else 1

    val error = when (uiState.passwordError) {
        PasswordsValidationError.EMPTY -> stringResource(R.string.empty_text_error)
        PasswordsValidationError.LENGTH_LESS_THAN_12 -> stringResource(R.string.password_length_error)
        PasswordsValidationError.NOT_EQUAL -> stringResource(R.string.passwords_not_equal_error)
        null -> null
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.create_password),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        )
        VerticalSpacer(of = 20.dp)
        StepperProgressIndicator(
            modifier = Modifier.padding(horizontal = 20.dp),
            currentSteps = 2,
            totalSteps = 2,
        )
        VerticalSpacer(of = (12 * paddingMultiplier).dp)
        JustChattingPasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            error = error,
            onValueChange = { password = it },
            passwordAction = PasswordAction(
                imeAction = ImeAction.Next,
                action = { focusManager.moveFocus(FocusDirection.Down) }
            ),
        )
        VerticalSpacer(of = (4 * paddingMultiplier).dp)
        JustChattingPasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password2,
            onValueChange = { password2 = it },
            label = stringResource(R.string.confirm_password),
            passwordAction = PasswordAction(
                imeAction = ImeAction.Done,
                action = { focusManager.clearFocus() }
            ),
        )
        VerticalSpacer(of = (12 * paddingMultiplier).dp)
        JustChattingButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.create_password),
            onClick = { createPassword(password, password2) },
        )
    }
}
