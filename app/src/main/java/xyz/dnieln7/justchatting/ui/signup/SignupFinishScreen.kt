package xyz.dnieln7.justchatting.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import xyz.dnieln7.justchatting.framework.extensions.isPortrait
import xyz.dnieln7.justchatting.ui.composable.VerticalSpacer

@Composable
fun SignupFinishScreen(
    uiState: SignupState.Register,
    onRegistered: () -> Unit,
    retry: () -> Unit,
) {
    val modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)

    Surface(modifier = Modifier.fillMaxSize()) {
        when (uiState.registerStatus) {
            RegisterStatus.Registering -> {
                SignupLoading(modifier = modifier)
            }

            RegisterStatus.Registered -> {
                SignupSuccess(modifier = modifier)
                LaunchedEffect(Unit) {
                    onRegistered()
                }
            }

            is RegisterStatus.Error -> {
                SignupError(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    error = uiState.registerStatus.message,
                    retry = retry
                )
            }
        }
    }
}

@Composable
fun SignupLoading(modifier: Modifier = Modifier) {
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
            text = "Registering, please wait...",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
fun SignupSuccess(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(150.dp),
            imageVector = Icons.Outlined.CheckCircle,
            contentDescription = "",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
        )
        VerticalSpacer(of = 48.dp)
        Text(
            text = "Account created successfully!",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
fun SignupError(modifier: Modifier = Modifier, error: String, retry: () -> Unit) {
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
            contentDescription = "",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
        )
        VerticalSpacer(of = (12 * paddingMultiplier).dp)
        Text(
            text = "There was an error, please try again",
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
        FilledTonalButton(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            onClick = retry,
        ) {
            Text("Retry")
        }
    }
}
