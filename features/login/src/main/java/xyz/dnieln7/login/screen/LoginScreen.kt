package xyz.dnieln7.login.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import xyz.dnieln7.composable.button.JustChattingNLSButton
import xyz.dnieln7.composable.spacer.VerticalFlexibleSpacer
import xyz.dnieln7.composable.spacer.VerticalSpacer
import xyz.dnieln7.composable.textfield.JustChattingPasswordTextField
import xyz.dnieln7.composable.textfield.JustChattingTextField
import xyz.dnieln7.composable.textfield.PasswordAction
import xyz.dnieln7.login.R

@Composable
fun LoginRoute(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navigateToSignup: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val uiState by loginViewModel.state.collectAsStateWithLifecycle()

    LoginScreen(
        uiState = uiState,
        login = loginViewModel::login,
        onLoggedIn = {
            navigateToHome()
            loginViewModel.onLoggedIn()
        },
        navigateToSignup = navigateToSignup,
    )
}

@Composable
fun LoginScreen(
    uiState: LoginState,
    login: (email: String, password: String) -> Unit,
    onLoggedIn: () -> Unit,
    navigateToSignup: () -> Unit,
) {
    val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        if (isPortrait) {
            Column {
                Logo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.80F),
                )
                LoginForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F),
                    loginState = uiState,
                    login = { email, password -> login(email, password) },
                    onLoggedIn = onLoggedIn,
                    navigateToSignup = navigateToSignup,
                )
            }
        } else {
            Row {
                Logo(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.80F),
                )
                LoginForm(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1F),
                    loginState = uiState,
                    login = { email, password -> login(email, password) },
                    onLoggedIn = onLoggedIn,
                    navigateToSignup = navigateToSignup,
                )
            }
        }
    }
}

@Composable
fun Logo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier.size(150.dp),
            painter = painterResource(R.drawable.ic_just_chatting),
            contentDescription = stringResource(R.string.just_chatting),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
        )
        VerticalSpacer(of = 24.dp)
        Text(
            text = stringResource(R.string.just_chatting),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
        )
    }
}

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    loginState: LoginState,
    login: (String, String) -> Unit,
    onLoggedIn: () -> Unit,
    navigateToSignup: () -> Unit,
) {
    val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
    val focusManager = LocalFocusManager.current

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val paddingValues = if (isPortrait) {
        PaddingValues(
            top = 40.dp,
            bottom = 30.dp,
            start = 20.dp,
            end = 20.dp,
        )
    } else {
        PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = 10.dp,
            bottom = 10.dp,
        )
    }

    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge.copy(
            bottomEnd = CornerSize(0.dp),
            bottomStart = CornerSize(0.dp),
        ),
        shadowElevation = 12.dp,
    ) {
        Column(modifier = Modifier.padding(paddingValues)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.welcome_back),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            )
            VerticalSpacer(of = 8.dp)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.login_to_your_account),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
            )
            VerticalFlexibleSpacer()
            JustChattingTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                label = stringResource(R.string.email),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
            )
            VerticalSpacer(of = 4.dp)
            JustChattingPasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it },
                passwordAction = PasswordAction(
                    imeAction = ImeAction.Done,
                    action = { focusManager.clearFocus() }
                ),
            )
            VerticalFlexibleSpacer()
            if (loginState is LoginState.Error) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = loginState.message,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.error),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                VerticalSpacer(of = 12.dp)
            }
            JustChattingNLSButton(
                modifier = Modifier.fillMaxWidth(),
                noneText = stringResource(R.string.login),
                successText = stringResource(R.string.logged_in),
                nlsButtonStatus = loginState.toNLSStatus(),
                onClick = { login(email, password) },
            )
            VerticalSpacer(of = 12.dp)
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navigateToSignup() },
                text = buildAnnotatedString {
                    append(stringResource(R.string.don_t_have_an_account))
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            color = MaterialTheme.colorScheme.primary,
                        ),
                        block = { append(stringResource(R.string.sign_up)) },
                    )
                },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }

    if (loginState == LoginState.Success) {
        LaunchedEffect(Unit) {
            onLoggedIn()
        }
    }
}
