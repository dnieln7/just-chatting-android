package xyz.dnieln7.profile.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.SearchOff
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import xyz.dnieln7.composable.alert.AlertAction
import xyz.dnieln7.composable.alert.JCErrorAlert
import xyz.dnieln7.composable.button.JCButton
import xyz.dnieln7.composable.progress.JCProgressIndicator
import xyz.dnieln7.composable.spacer.VerticalFlexibleSpacer
import xyz.dnieln7.composable.spacer.VerticalSpacer
import xyz.dnieln7.domain.model.User
import xyz.dnieln7.profile.R

@Composable
fun ProfileScreen(
    uiState: ProfileState,
    getUser: () -> Unit,
    logout: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(top = 20.dp, bottom = 18.dp),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.profile),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
    ) { paddingValues ->
        ElevatedCard(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer),
            shape = MaterialTheme.shapes.large.copy(
                bottomStart = CornerSize(0.dp),
                bottomEnd = CornerSize(0.dp),
            ),
        ) {
            when (uiState) {
                ProfileState.Loading -> JCProgressIndicator(
                    modifier = Modifier.fillMaxSize()
                )

                ProfileState.UserNotFound -> JCErrorAlert(
                    icon = Icons.Rounded.SearchOff,
                    error = stringResource(R.string.could_not_load_profile),
                    alertAction = AlertAction(
                        text = stringResource(R.string.try_again),
                        onClick = getUser,
                    ),
                )

                is ProfileState.UserFound -> Profile(user = uiState.data, logout = logout)

                is ProfileState.LogoutError -> JCErrorAlert(
                    icon = Icons.Rounded.Error,
                    error = uiState.message,
                    alertAction = AlertAction(
                        text = stringResource(R.string.try_again),
                        onClick = logout,
                    ),
                )

                ProfileState.LoggedOut -> {
                    LaunchedEffect(Unit) {
                        navigateToLogin()
                    }
                }
            }
        }
    }
}

@Composable
fun Profile(user: User, logout: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(300.dp)
                .clip(MaterialTheme.shapes.extraLarge),
            placeholder = rememberVectorPainter(image = Icons.Rounded.Image),
            contentDescription = user.username,
            contentScale = ContentScale.FillWidth,
            model = ImageRequest.Builder(context)
                .data("https://ui-avatars.com/api/?name=${user.username}&size=512&background=2196F3&color=FFF&bold=true")
                .crossfade(true)
                .build(),
        )
        VerticalSpacer(of = 16.dp)
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = user.username,
            style = MaterialTheme.typography.headlineMedium,
        )
        VerticalSpacer(of = 8.dp)
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = user.email,
            style = MaterialTheme.typography.titleSmall,
        )
        VerticalFlexibleSpacer()
        JCButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.logout),
            onClick = logout,
        )
    }
}
