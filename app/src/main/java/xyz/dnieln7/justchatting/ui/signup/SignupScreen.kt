package xyz.dnieln7.justchatting.ui.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.dnieln7.justchatting.R
import xyz.dnieln7.justchatting.domain.validation.EmailValidationError
import xyz.dnieln7.justchatting.domain.validation.PasswordsValidationError
import xyz.dnieln7.justchatting.domain.validation.UsernameValidationError
import xyz.dnieln7.justchatting.framework.extensions.isPortrait
import xyz.dnieln7.justchatting.ui.composable.PasswordOutlinedTextField
import xyz.dnieln7.justchatting.ui.composable.StepperProgressIndicator
import xyz.dnieln7.justchatting.ui.composable.VerticalSpacer

@Composable
fun SignupRoute(
    signupViewModel: SignupViewModel = viewModel(),
    navigateToHome: () -> Unit,
) {
    val uiState by signupViewModel.state.collectAsStateWithLifecycle()

    println(uiState)

    when (uiState) {
        is SignupState.CreateUser -> {
            CreateUserScreen(
                uiState = uiState as SignupState.CreateUser,
                createUser = signupViewModel::createUser,
            )
        }

        is SignupState.CreatePassword -> {
            CreatePasswordScreen(
                uiState = uiState as SignupState.CreatePassword,
                createPassword = signupViewModel::createPassword,
            )
        }

        is SignupState.Register -> SignupFinishScreen(
            uiState = uiState as SignupState.Register,
            onRegistered = {},
            retry = signupViewModel::register,
        )
    }
}

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

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Create user",
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
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(R.string.email)) },
                isError = uiState.emailValidationError != null,
                supportingText = {
                    val error = when (uiState.emailValidationError) {
                        EmailValidationError.EMPTY -> stringResource(R.string.empty_text_error)
                        EmailValidationError.NOT_AN_EMAIL -> stringResource(R.string.email_not_valid_error)
                        null -> ""
                    }

                    Text(text = error)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                shape = MaterialTheme.shapes.small,
            )
            VerticalSpacer(of = (4 * paddingMultiplier).dp)
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(R.string.username)) },
                isError = uiState.usernameValidationError != null,
                supportingText = {
                    val error = when (uiState.usernameValidationError) {
                        UsernameValidationError.EMPTY -> stringResource(R.string.empty_text_error)
                        null -> ""
                    }

                    Text(text = error)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                shape = MaterialTheme.shapes.small,
            )
            VerticalSpacer(of = (12 * paddingMultiplier).dp)
            FilledTonalButton(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                onClick = { createUser(email, username) },
            ) {
                Text("Next")
            }
        }
    }
}

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

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Create a password",
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
            PasswordOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                error = error,
                onValueChange = { password = it },
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
            VerticalSpacer(of = (4 * paddingMultiplier).dp)
            PasswordOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password2,
                onValueChange = { password2 = it },
                label = "Confirm password",
                onDone = { focusManager.clearFocus() }
            )
            VerticalSpacer(of = (12 * paddingMultiplier).dp)
            FilledTonalButton(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                onClick = { createPassword(password, password2) },
            ) {
                Text("Confirm")
            }
        }
    }
}
