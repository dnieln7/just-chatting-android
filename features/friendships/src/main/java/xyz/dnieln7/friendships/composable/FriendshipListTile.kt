package xyz.dnieln7.friendships.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PersonRemove
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.dnieln7.composable.button.JCIconButton
import xyz.dnieln7.composable.spacer.HorizontalFlexibleSpacer
import xyz.dnieln7.composable.spacer.HorizontalSpacer
import xyz.dnieln7.composable.spacer.VerticalSpacer
import xyz.dnieln7.composable.theme.JCTheme
import xyz.dnieln7.domain.model.Friendship
import xyz.dnieln7.friendships.R
import xyz.dnieln7.friendships.screen.StatefulFriendship

@Composable
fun FriendshipListTile(
    modifier: Modifier = Modifier,
    friendship: StatefulFriendship,
    onClick: () -> Unit,
    onDelete: (Friendship) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
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
                text = friendship.data.username,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            )
            VerticalSpacer(of = 4.dp)
            Text(
                text = friendship.data.email,
                style = MaterialTheme.typography.titleMedium,
            )
        }
        HorizontalFlexibleSpacer()
        if (friendship.isLoading) {
            CircularProgressIndicator(strokeCap = StrokeCap.Round)
        } else {
            JCIconButton(
                icon = Icons.Rounded.PersonRemove,
                contentDescription = stringResource(R.string.unfriend),
                onClick = { onDelete(friendship.data) }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FriendshipListTilePreview() {
    val friendship = Friendship(id = "id", email = "email@example.com", username = "example")

    JCTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                FriendshipListTile(
                    friendship = StatefulFriendship(isLoading = true, data = friendship),
                    onClick = {},
                    onDelete = {})
            }
        }
    }
}
