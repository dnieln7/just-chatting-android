package xyz.dnieln7.chats.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import xyz.dnieln7.chats.R
import xyz.dnieln7.chats.composable.ChatListTile
import xyz.dnieln7.composable.alert.AlertAction
import xyz.dnieln7.composable.alert.JCErrorAlert
import xyz.dnieln7.composable.progress.JCProgressIndicator
import xyz.dnieln7.composable.pullrefresh.PullRefresh
import xyz.dnieln7.domain.model.Chat

@Composable
fun ChatsScreen(
    uiState: ChatsState,
    getChats: () -> Unit,
    navigateToChat: (Chat) -> Unit,
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
                    text = stringResource(R.string.chats),
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
                ChatsState.Loading -> JCProgressIndicator(
                    modifier = Modifier.fillMaxSize()
                )

                is ChatsState.Error -> JCErrorAlert(
                    modifier = Modifier.fillMaxSize(),
                    icon = Icons.Rounded.Error,
                    error = uiState.message,
                    alertAction = AlertAction(
                        text = stringResource(R.string.try_again),
                        onClick = getChats,
                    ),
                )

                is ChatsState.Success -> PullRefresh(onRefresh = getChats) {
                    LazyColumn {
                        items(items = uiState.data, key = { it.id }) {
                            ChatListTile(
                                chat = it,
                                onClick = { navigateToChat(it) },
                            )
                        }
                    }
                }
            }
        }
    }
}
