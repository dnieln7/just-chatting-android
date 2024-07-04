package xyz.dnieln7.chat.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.dnieln7.composable.spacer.VerticalSpacer
import xyz.dnieln7.composable.theme.JCTheme
import xyz.dnieln7.domain.extension.isToday
import xyz.dnieln7.domain.model.Message
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun MessageListTile(
    message: Message,
    isMe: (String) -> Boolean,
    getUsername: (String) -> String,
) {
    val me = isMe(message.userID)

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (me) Alignment.CenterEnd else Alignment.CenterStart,
    ) {
        Card(
            shape = if (me) MaterialTheme.shapes.medium.copy(
                bottomEnd = CornerSize(0.dp),
            )
            else MaterialTheme.shapes.medium.copy(
                bottomStart = CornerSize(0.dp),
            ),
            colors = if (me) CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            )
            else CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
        ) {
            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp,
                        start = 16.dp,
                        end = 8.dp
                    ),
            ) {
                Text(
                    text = getUsername(message.userID),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                )
                VerticalSpacer(of = 4.dp)
                Text(
                    text = message.message,
                    style = MaterialTheme.typography.bodyMedium,
                )
                VerticalSpacer(of = 4.dp)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (message.createdAt.isToday()) {
                        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).format(
                            message.createdAt,
                        )
                    } else {
                        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(
                            message.createdAt,
                        )
                    },
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MessageListTilePreview() {
    val message = Message(
        id = "8e722187-d48b-457d-b44e-68aed1f04aca",
        chatID = "500a8c53-b38f-4221-8a6b-0065c522632d",
        userID = "0fac06d5-bb77-4def-90f7-8c0c1fcffda7",
        message = "",
        createdAt = ZonedDateTime.now(),
        updatedAt = ZonedDateTime.now(),
    )

    JCTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
            ) {
                MessageListTile(
                    message = message.copy(
                        message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"
                    ),
                    getUsername = { "Not me" },
                    isMe = { false },
                )
                VerticalSpacer(of = 12.dp)
                MessageListTile(
                    message = message.copy(
                        message = "Its been so long that i have seen you",
                        createdAt = message.createdAt.minusDays(1)
                    ),
                    getUsername = { "Not me" },
                    isMe = { false },
                )
                VerticalSpacer(of = 12.dp)
                MessageListTile(
                    message = message.copy(
                        message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"
                    ),
                    getUsername = { "Me" },
                    isMe = { true },
                )
                VerticalSpacer(of = 12.dp)
                MessageListTile(
                    message = message.copy(
                        message = "I am missing you guys so much meet you soon",
                        createdAt = message.createdAt.minusDays(1)
                    ),
                    getUsername = { "Me" },
                    isMe = { true },
                )
            }
        }
    }
}