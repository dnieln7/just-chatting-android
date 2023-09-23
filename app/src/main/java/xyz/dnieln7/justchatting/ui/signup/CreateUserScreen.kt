package xyz.dnieln7.justchatting.ui.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import xyz.dnieln7.justchatting.R
import xyz.dnieln7.justchatting.domain.validation.EmailValidationError
import xyz.dnieln7.justchatting.domain.validation.UsernameValidationError
import xyz.dnieln7.justchatting.framework.extensions.isPortrait
import xyz.dnieln7.justchatting.ui.composable.JustChattingButton
import xyz.dnieln7.justchatting.ui.composable.JustChattingTextField
import xyz.dnieln7.justchatting.ui.composable.StepperProgressIndicator
import xyz.dnieln7.justchatting.ui.composable.VerticalSpacer

@Composable
fun CreateUserScreen(
    uiState: SignupState.CreateUser,
    createUser: (email: String, username: String) -> Unit,
) {
    val isPortrait = LocalConfiguration.current.isPortrait()
    val focusManager = LocalFocusManager.current

    var email by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }

    val paddingMultiplier = if (isPortrait) 4 else 1

    val emailError = when (uiState.emailValidationError) {
        EmailValidationError.EMPTY -> stringResource(R.string.empty_text_error)
        EmailValidationError.NOT_AN_EMAIL -> stringResource(R.string.email_not_valid_error)
        null -> null
    }

    val usernameError = when (uiState.usernameValidationError) {
        UsernameValidationError.EMPTY -> stringResource(R.string.empty_text_error)
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
            text = stringResource(R.string.create_user),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        )
        VerticalSpacer(of = 20.dp)
        StepperProgressIndicator(
            modifier = Modifier.padding(horizontal = 20.dp),
            currentSteps = 1,
            totalSteps = 2,
        )
        VerticalSpacer(of = (12 * paddingMultiplier).dp)
        JustChattingTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = stringResource(R.string.email),
            error = emailError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
        )
        VerticalSpacer(of = (4 * paddingMultiplier).dp)
        JustChattingTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = { username = it },
            label = stringResource(R.string.username),
            error = usernameError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
        )
        VerticalSpacer(of = (12 * paddingMultiplier).dp)
        JustChattingButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.create_user),
            onClick = { createUser(email, username) },
        )
    }
}
