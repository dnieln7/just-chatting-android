package xyz.dnieln7.justchatting.ui.signup.createuser

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.dnieln7.justchatting.R
import xyz.dnieln7.justchatting.framework.extension.isPortrait
import xyz.dnieln7.justchatting.framework.validation.stringFromEmailValidationError
import xyz.dnieln7.justchatting.framework.validation.stringFromSimpleTextValidationError
import xyz.dnieln7.justchatting.ui.composable.JustChattingNLSButton
import xyz.dnieln7.justchatting.ui.composable.JustChattingTextField
import xyz.dnieln7.justchatting.ui.composable.StepperProgressIndicator
import xyz.dnieln7.justchatting.ui.composable.VerticalSpacer
import xyz.dnieln7.justchatting.ui.signup.SignupViewModel

@Composable
fun CreateUserRoute(
    signupViewModel: SignupViewModel = viewModel(),
    navigateToCreatePassword: () -> Unit,
) {
    val uiState by signupViewModel.createUserState.collectAsStateWithLifecycle()

    if (uiState == CreateUserState.Success) {
        LaunchedEffect(Unit) {
            navigateToCreatePassword()
            signupViewModel.onUserCreated()
        }
    } else {
        CreateUserScreen(
            uiState = uiState,
            createUser = signupViewModel::createUser,
        )
    }
}

@Composable
fun CreateUserScreen(
    uiState: CreateUserState,
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
                error = stringFromEmailValidationError(uiState.asError()?.emailError),
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
                error = stringFromSimpleTextValidationError(uiState.asError()?.usernameError),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
            )
            if (uiState is CreateUserState.Error && uiState.error != null) {
                VerticalSpacer(of = 12.dp)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = uiState.error,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.error),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                VerticalSpacer(of = 12.dp)
            }
            VerticalSpacer(of = (12 * paddingMultiplier).dp)
            JustChattingNLSButton(
                modifier = Modifier.fillMaxWidth(),
                noneText = stringResource(R.string.create_user),
                onClick = { createUser(email, username) },
                nlsButtonStatus = uiState.toNLSStatus(),
            )
        }
    }
}
