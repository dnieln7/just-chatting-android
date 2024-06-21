package xyz.dnieln7.signup.screen.createuser

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
import xyz.dnieln7.composable.button.JCStatefulButton
import xyz.dnieln7.composable.extension.asString
import xyz.dnieln7.composable.extension.isPortrait
import xyz.dnieln7.composable.progress.JCStepper
import xyz.dnieln7.composable.spacer.VerticalSpacer
import xyz.dnieln7.composable.textfield.JCTextField
import xyz.dnieln7.signup.R

@Composable
fun CreateUserScreen(
    uiState: CreateUserState,
    createUser: (email: String, username: String) -> Unit,
    resetState: () -> Unit,
    form: CreateUserForm,
    validation: CreateUserFormValidation,
    updateEmail: (String) -> Unit,
    updateUsername: (String) -> Unit,
    navigateToCreatePassword: (String, String) -> Unit,
) {
    val isPortrait = LocalConfiguration.current.isPortrait()
    val focusManager = LocalFocusManager.current

    val paddingMultiplier = if (isPortrait) 4 else 1

    if (uiState is CreateUserState.Success) {
        LaunchedEffect(Unit) {
            navigateToCreatePassword(uiState.email, uiState.username)
            resetState()
        }
    } else {
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
                JCStepper(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    stepsProgress = 1,
                    totalSteps = 2,
                )
                VerticalSpacer(of = (12 * paddingMultiplier).dp)
                JCTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = form.email,
                    onValueChange = updateEmail,
                    label = stringResource(R.string.email),
                    error = validation.emailValidation.asString(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                )
                VerticalSpacer(of = (4 * paddingMultiplier).dp)
                JCTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = form.username,
                    onValueChange = updateUsername,
                    label = stringResource(R.string.username),
                    error = validation.usernameValidation.asString(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                )
                if (uiState is CreateUserState.Error) {
                    VerticalSpacer(of = 12.dp)
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = uiState.message,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.error),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                VerticalSpacer(of = (12 * paddingMultiplier).dp)
                JCStatefulButton(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = validation.isValid(),
                    noneText = stringResource(R.string.create_user),
                    successText = stringResource(R.string.user_created),
                    onClick = { createUser(form.email, form.username) },
                    statefulButtonStatus = uiState.toStatefulButtonStatus(),
                )
            }
        }
    }
}
