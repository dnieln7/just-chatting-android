package xyz.dnieln7.justchatting.ui.signup.createuser

import androidx.activity.ComponentActivity
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
import androidx.compose.ui.platform.LocalContext
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
import xyz.dnieln7.justchatting.framework.extensions.isPortrait
import xyz.dnieln7.justchatting.framework.validation.stringFromEmailValidationError
import xyz.dnieln7.justchatting.framework.validation.stringFromUsernameValidationError
import xyz.dnieln7.justchatting.ui.composable.JustChattingButton
import xyz.dnieln7.justchatting.ui.composable.JustChattingTextField
import xyz.dnieln7.justchatting.ui.composable.StepperProgressIndicator
import xyz.dnieln7.justchatting.ui.composable.VerticalSpacer
import xyz.dnieln7.justchatting.ui.signup.SignupViewModel

@Composable
fun CreateUserRoute(
    signupViewModel: SignupViewModel = viewModel(viewModelStoreOwner = (LocalContext.current as ComponentActivity)),
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
                error = stringFromUsernameValidationError(uiState.asError()?.usernameError),
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
}
