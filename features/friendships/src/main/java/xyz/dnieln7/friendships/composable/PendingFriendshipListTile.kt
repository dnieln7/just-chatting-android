package xyz.dnieln7.friendships.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.NotInterested
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.dnieln7.composable.button.JustChattingIconButton
import xyz.dnieln7.composable.spacer.HorizontalFlexibleSpacer
import xyz.dnieln7.composable.spacer.HorizontalSpacer
import xyz.dnieln7.composable.spacer.VerticalSpacer
import xyz.dnieln7.composable.theme.JustChattingTheme
import xyz.dnieln7.domain.model.Friendship
import xyz.dnieln7.friendships.R

@Composable
fun PendingFriendshipListTile(
    modifier: Modifier = Modifier,
    friendship: Friendship,
    onAccept: () -> Unit,
    onReject: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(8.dp)
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Rounded.Person,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        HorizontalSpacer(of = 12.dp)
        Column {
            Text(
                text = friendship.username,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            )
            VerticalSpacer(of = 4.dp)
            Text(
                text = friendship.email,
                style = MaterialTheme.typography.titleMedium,
            )
        }
        HorizontalFlexibleSpacer()
        JustChattingIconButton(
            icon = Icons.Rounded.Check,
            contentDescription = stringResource(R.string.accept),
            onClick = onAccept,
        )
        JustChattingIconButton(
            icon = Icons.Rounded.NotInterested,
            contentDescription = stringResource(R.string.reject),
            onClick = onReject,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PendingFriendshipListTilePreview() {
    val friendship = Friendship(id = "id", email = "email@example.com", username = "example")

    JustChattingTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                PendingFriendshipListTile(
                    friendship = friendship,
                    onAccept = {},
                    onReject = {})
            }
        }
    }
}
