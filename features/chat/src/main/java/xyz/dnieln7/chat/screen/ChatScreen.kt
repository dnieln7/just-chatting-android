package xyz.dnieln7.chat.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.SearchOff
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import xyz.dnieln7.chat.R
import xyz.dnieln7.chat.composable.MessageListTile
import xyz.dnieln7.chat.composable.MessageTextField
import xyz.dnieln7.composable.alert.AlertAction
import xyz.dnieln7.composable.alert.JCErrorAlert
import xyz.dnieln7.composable.button.JCIconButton
import xyz.dnieln7.composable.progress.JCProgressIndicator
import xyz.dnieln7.composable.spacer.HorizontalSpacer

@Composable
fun ChatScreen(
    chatState: ChatState,
    connectionState: ChatConnectionState,
    isMe: (String) -> Boolean,
    getUsername: (String) -> String,
    onAction: (ChatAction) -> Unit,
) {
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(top = 20.dp, bottom = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HorizontalSpacer(of = 4.dp)
                JCIconButton(
                    icon = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.go_back),
                    onClick = { onAction(ChatAction.OnBackClick) },
                )
                HorizontalSpacer(of = 8.dp)
                if (chatState is ChatState.Success) {
                    Text(
                        text = stringResource(
                            id = R.string.chat_with_friendship,
                            formatArgs = arrayOf(chatState.data.friend.username),
                        ),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                if (chatState is ChatState.Error) {
                    TextButton(
                        onClick = { onAction(ChatAction.OnLoadChatRetryClick) },
                        content = {
                            Text(text = stringResource(R.string.try_again))
                        }
                    )
                }
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
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F)
                ) {
                    when (connectionState.status) {
                        ChatConnectionStatus.NONE,
                        ChatConnectionStatus.FETCHING_MESSAGES,
                        ChatConnectionStatus.CONNECTING -> JCProgressIndicator(
                            modifier = Modifier.fillMaxSize()
                        )

                        ChatConnectionStatus.MESSAGES_ERROR -> JCErrorAlert(
                            modifier = Modifier.fillMaxSize(),
                            icon = Icons.Rounded.SearchOff,
                            error = stringResource(R.string.could_not_load_messages),
                            alertAction = AlertAction(
                                text = stringResource(R.string.try_again),
                                onClick = { onAction(ChatAction.OnLoadMessagesRetryClick) },
                            ),
                        )

                        ChatConnectionStatus.CONNECTION_ERROR -> JCErrorAlert(
                            modifier = Modifier.fillMaxSize(),
                            icon = Icons.Rounded.SearchOff,
                            error = stringResource(R.string.could_not_connect),
                            alertAction = AlertAction(
                                text = stringResource(R.string.try_again),
                                onClick = { onAction(ChatAction.OnReconnectClick) },
                            ),
                        )

                        ChatConnectionStatus.CONNECTED -> {
                            LazyColumn(
                                modifier = Modifier.padding(12.dp),
                                state = listState,
                                reverseLayout = true,
                            ) {
                                items(items = connectionState.messages, key = { it.id }) {
                                    MessageListTile(
                                        message = it,
                                        isMe = isMe,
                                        getUsername = getUsername,
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }

                            if (connectionState.messages.isNotEmpty()) {
                                LaunchedEffect(connectionState) {
                                    listState.animateScrollToItem(index = 0)
                                }
                            }
                        }
                    }
                }
                MessageTextField(
                    enabled = connectionState.status == ChatConnectionStatus.CONNECTED,
                    onSend = { onAction(ChatAction.OnSendMessageClick(it)) },
                )
            }
        }
    }
}
