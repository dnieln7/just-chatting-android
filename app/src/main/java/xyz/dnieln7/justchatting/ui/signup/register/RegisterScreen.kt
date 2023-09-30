package xyz.dnieln7.justchatting.ui.signup.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import xyz.dnieln7.justchatting.R
import xyz.dnieln7.justchatting.framework.extensions.isPortrait
import xyz.dnieln7.justchatting.ui.composable.JustChattingButton
import xyz.dnieln7.justchatting.ui.composable.VerticalSpacer
import xyz.dnieln7.justchatting.ui.composable.launchSaveable
import xyz.dnieln7.justchatting.ui.signup.SignupViewModel

@Composable
fun RegisterRoute(
    signupViewModel: SignupViewModel,
    navigateToHome: () -> Unit,
) {
    val uiState by signupViewModel.registerState.collectAsStateWithLifecycle()

    launchSaveable { signupViewModel.register() }

    RegisterScreen(
        uiState = uiState,
        onRegistered = { navigateToHome() },
        retry = signupViewModel::register,
    )
}

@Composable
fun RegisterScreen(
    uiState: RegisterState,
    onRegistered: () -> Unit,
    retry: () -> Unit,
) {
    val modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)

    when (uiState) {
        RegisterState.Loading -> RegisterLoading(modifier = modifier)

        RegisterState.Success -> {
            RegisterSuccess(modifier = modifier)
            LaunchedEffect(Unit) {
                onRegistered()
            }
        }

        is RegisterState.Error -> {
            RegisterError(
                modifier = modifier,
                error = uiState.message,
                retry = retry,
            )
        }
    }
}

@Composable
fun RegisterLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(150.dp),
            strokeCap = StrokeCap.Round,
            strokeWidth = 15.dp,
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.secondaryContainer,
        )
        VerticalSpacer(of = 48.dp)
        Text(
            text = stringResource(R.string.registering_please_wait),
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
fun RegisterSuccess(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(150.dp),
            imageVector = Icons.Outlined.CheckCircle,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
        )
        VerticalSpacer(of = 48.dp)
        Text(
            text = stringResource(R.string.account_created_successfully),
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
fun RegisterError(modifier: Modifier = Modifier, error: String, retry: () -> Unit) {
    val isPortrait = LocalConfiguration.current.isPortrait()

    val paddingMultiplier = if (isPortrait) 4 else 1
    val maxLines = if (isPortrait) 4 else 2

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(150.dp),
            imageVector = Icons.Outlined.ErrorOutline,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
        )
        VerticalSpacer(of = (12 * paddingMultiplier).dp)
        Text(
            text = stringResource(R.string.generic_error),
            style = MaterialTheme.typography.titleLarge,
        )
        VerticalSpacer(of = 8.dp)
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = error,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.error
            ),
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
        )
        VerticalSpacer(of = (12 * paddingMultiplier).dp)
        JustChattingButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.retry),
            onClick = retry,
        )
    }
}
