package xyz.dnieln7.friendships.screen.pendingfriendships

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.Pending
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import xyz.dnieln7.composable.alert.AlertAction
import xyz.dnieln7.composable.alert.JCAlert
import xyz.dnieln7.composable.alert.JCErrorAlert
import xyz.dnieln7.composable.progress.JCProgressIndicator
import xyz.dnieln7.composable.pullrefresh.PullRefresh
import xyz.dnieln7.domain.model.Friendship
import xyz.dnieln7.friendships.R
import xyz.dnieln7.friendships.composable.PendingFriendshipListTile

@Composable
fun PendingFriendshipsScreen(
    uiState: PendingFriendshipsState,
    getPendingFriendships: () -> Unit,
    acceptFriendship: (Friendship) -> Unit,
    rejectFriendship: (Friendship) -> Unit,
) {
    when (uiState) {
        PendingFriendshipsState.Loading -> JCProgressIndicator(
            modifier = Modifier.fillMaxSize()
        )

        is PendingFriendshipsState.Success -> {
            if (uiState.data.isEmpty()) {
                JCAlert(
                    modifier = Modifier.fillMaxSize(),
                    icon = Icons.Rounded.Pending,
                    text = stringResource(R.string.empty_pending_friendships),
                    alertAction = AlertAction(
                        text = stringResource(R.string.try_again),
                        onClick = getPendingFriendships,
                    ),
                )
            } else {
                PullRefresh(onRefresh = getPendingFriendships) {
                    LazyColumn {
                        items(items = uiState.data, key = { it.data.id }) {
                            PendingFriendshipListTile(
                                friendship = it,
                                onAccept = acceptFriendship,
                                onReject = rejectFriendship,
                            )
                        }
                        item { Spacer(Modifier.height(80.dp)) }
                    }
                }
            }
        }

        is PendingFriendshipsState.Error -> JCErrorAlert(
            icon = Icons.Rounded.Error,
            error = uiState.message,
            alertAction = AlertAction(
                text = stringResource(R.string.try_again),
                onClick = getPendingFriendships,
            ),
        )
    }
}
