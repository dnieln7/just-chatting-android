package xyz.dnieln7.chats.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.dnieln7.chats.R
import xyz.dnieln7.composable.spacer.HorizontalSpacer
import xyz.dnieln7.composable.spacer.VerticalSpacer
import xyz.dnieln7.composable.theme.JCTheme
import xyz.dnieln7.domain.model.Chat
import xyz.dnieln7.domain.model.Participant
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun ChatListTile(
    chat: Chat,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(12.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(50.dp),
            imageVector = Icons.Rounded.Chat,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )
        HorizontalSpacer(of = 16.dp)
        Column(modifier = Modifier.weight(1F)) {
            Text(
                text = stringResource(
                    id = R.string.chat_with_friendship,
                    formatArgs = arrayOf(chat.friend.username),
                ),
                style = MaterialTheme.typography.titleLarge,
            )
            VerticalSpacer(of = 4.dp)
            Text(
                text = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(
                    chat.createdAt,
                ),
                style = MaterialTheme.typography.titleSmall,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ChatListTilePreview() {
    val chat = Chat(
        id = "fake_id",
        me = Participant(
            id = "fake_me_id",
            email = "fake_me_email",
            username = "fake_me_username",
        ),
        creator = Participant(
            id = "fake_creator_id",
            email = "fake_creator_email",
            username = "fake_creator_username",
        ),
        friend = Participant(
            id = "fake_friend_id",
            email = "fake_friend_email",
            username = "fake_friend_username",
        ),
        createdAt = ZonedDateTime.now(),
        updatedAt = ZonedDateTime.now(),
    )

    JCTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                ChatListTile(chat = chat, onClick = {})
            }
        }
    }
}
