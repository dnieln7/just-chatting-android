package xyz.dnieln7.justchatting.ui.login

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
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
import xyz.dnieln7.justchatting.R
import xyz.dnieln7.justchatting.ui.composable.PasswordOutlinedTextField
import xyz.dnieln7.justchatting.ui.composable.VerticalFlexibleSpacer
import xyz.dnieln7.justchatting.ui.composable.VerticalSpacer

@Composable
fun LoginScreen() {
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
                    loginState = LoginState.None,
                    onLogin = { email, password -> },
                    onSignup = {},
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
                    loginState = LoginState.None,
                    onLogin = { email, password -> },
                    onSignup = {},
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
            contentDescription = stringResource(R.string.app_name),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
        )
        VerticalSpacer(of = 24.dp)
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
        )
    }
}

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    loginState: LoginState,
    onLogin: (email: String, password: String) -> Unit,
    onSignup: () -> Unit,
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
                text = "Welcome Back",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            )
            VerticalSpacer(of = 8.dp)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Login to your account",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
            )
            VerticalFlexibleSpacer()
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                shape = MaterialTheme.shapes.small,
            )
            VerticalSpacer(of = 12.dp)
            PasswordOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it },
                onDone = { focusManager.clearFocus() }
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
            FilledTonalButton(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                onClick = {
                    if (loginState != LoginState.Loading && loginState != LoginState.Success) {
                        onLogin(email, password)
                    }
                },
            ) {
                when (loginState) {
                    LoginState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    }

                    LoginState.Success -> {
                        Icon(imageVector = Icons.Outlined.Check, contentDescription = "Logged in")
                    }

                    else -> {
                        Text("Login")
                    }
                }
            }
            VerticalSpacer(of = 12.dp)
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSignup() },
                text = buildAnnotatedString {
                    append("Don't have an account? ")
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            color = MaterialTheme.colorScheme.primary,
                        ),
                        block = { append("Sign up") },
                    )
                },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
