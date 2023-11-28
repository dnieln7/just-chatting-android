package xyz.dnieln7.friendships.screen.friendships

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Contacts
import androidx.compose.material.icons.rounded.Error
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
import xyz.dnieln7.friendships.composable.FriendshipListTile

@Composable
fun FriendshipsScreen(
    uiState: FriendshipsState,
    getFriendships: () -> Unit,
    deleteFriendship: (Friendship) -> Unit
) {
    when (uiState) {
        FriendshipsState.Loading -> JCProgressIndicator(
            modifier = Modifier.fillMaxSize()
        )

        is FriendshipsState.Success -> {
            if (uiState.data.isEmpty()) {
                JCAlert(
                    modifier = Modifier.fillMaxSize(),
                    icon = Icons.Rounded.Contacts,
                    text = stringResource(R.string.empty_friendships),
                    alertAction = AlertAction(
                        text = stringResource(R.string.try_again),
                        onClick = getFriendships,
                    ),
                )
            } else {
                PullRefresh(onRefresh = getFriendships) {
                    LazyColumn {
                        items(items = uiState.data, key = { it.data.id }) {
                            FriendshipListTile(
                                friendship = it,
                                onClick = { println("onClick") },
                                onDelete = deleteFriendship,
                            )
                        }
                        item { Spacer(Modifier.height(80.dp)) }
                    }
                }
            }
        }

        is FriendshipsState.Error -> JCErrorAlert(
            icon = Icons.Rounded.Error,
            error = uiState.message,
            alertAction = AlertAction(
                text = stringResource(R.string.try_again),
                onClick = getFriendships,
            ),
        )
    }
}
