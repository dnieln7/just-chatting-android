package xyz.dnieln7.signup.screen.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import xyz.dnieln7.composable.button.JCButton
import xyz.dnieln7.composable.extension.isPortrait
import xyz.dnieln7.composable.progress.JCProgressIndicator
import xyz.dnieln7.composable.spacer.VerticalSpacer
import xyz.dnieln7.signup.R

@Composable
fun RegisterScreen(
    uiState: RegisterState,
    onAction: (RegisterAction) -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            RegisterState.Loading -> RegisterLoading(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
            )

            RegisterState.Success -> {
                RegisterSuccess(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                )
                LaunchedEffect(Unit) {
                    onAction(RegisterAction.OnUserRegistered)
                }
            }

            is RegisterState.Error -> {
                RegisterError(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    error = uiState.message,
                    retry = { onAction(RegisterAction.OnRegisterRetryClick) },
                )
            }
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
        JCProgressIndicator()
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
            imageVector = Icons.Rounded.CheckCircle,
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
            imageVector = Icons.Rounded.Error,
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
        JCButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.retry),
            onClick = retry,
        )
    }
}
