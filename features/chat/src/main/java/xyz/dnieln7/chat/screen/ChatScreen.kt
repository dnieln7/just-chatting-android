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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import xyz.dnieln7.composable.button.JCIconButton
import xyz.dnieln7.composable.progress.JCProgressIndicator
import xyz.dnieln7.composable.spacer.HorizontalSpacer
import xyz.dnieln7.data.websocket.model.WebSocketStatus

@Composable
fun ChatScreen(
    uiState: ChatState,
    isMe: (String) -> Boolean,
    getUsername: (String) -> String,
    sendMessage: (String) -> Unit,
    navigateBack: () -> Unit,
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
                    onClick = navigateBack,
                )
                HorizontalSpacer(of = 8.dp)
                if (uiState.chat != null) {
                    Text(
                        text = stringResource(
                            id = R.string.chat_with_friendship,
                            formatArgs = arrayOf(uiState.chat.friend.username),
                        ),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
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
                    if (uiState.loadingMessages || uiState.chat == null) {
                        JCProgressIndicator(modifier = Modifier.fillMaxSize())
                    } else {
                        LazyColumn(
                            modifier = Modifier.padding(12.dp),
                            state = listState,
                            reverseLayout = true,
                        ) {
                            items(items = uiState.messages, key = { it.id }) {
                                MessageListTile(
                                    message = it,
                                    isMe = isMe,
                                    getUsername = getUsername,
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }

                        if (uiState.messages.isNotEmpty()) {
                            LaunchedEffect(uiState) { listState.animateScrollToItem(index = 0) }
                        }
                    }
                }
                MessageTextField(
                    enabled = uiState.status == WebSocketStatus.CONNECTED,
                    onSend = sendMessage,
                )
            }
        }
    }
}
