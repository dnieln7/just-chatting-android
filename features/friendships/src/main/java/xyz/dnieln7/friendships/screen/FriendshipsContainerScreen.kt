package xyz.dnieln7.friendships.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import xyz.dnieln7.composable.spacer.VerticalSpacer
import xyz.dnieln7.composable.tab.JCTabs
import xyz.dnieln7.domain.model.Friendship
import xyz.dnieln7.domain.model.User
import xyz.dnieln7.friendships.R
import xyz.dnieln7.friendships.screen.addfriendship.AddFriendshipScreen
import xyz.dnieln7.friendships.screen.addfriendship.AddFriendshipState
import xyz.dnieln7.friendships.screen.friendships.FriendshipsScreen
import xyz.dnieln7.friendships.screen.friendships.FriendshipsState
import xyz.dnieln7.friendships.screen.pendingfriendships.PendingFriendshipsScreen
import xyz.dnieln7.friendships.screen.pendingfriendships.PendingFriendshipsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendshipsContainerScreen(
    friendshipsState: FriendshipsState,
    getFriendships: () -> Unit,
    deleteFriendship: (Friendship) -> Unit,
    pendingFriendshipsState: PendingFriendshipsState,
    getPendingFriendships: () -> Unit,
    acceptFriendship: (Friendship) -> Unit,
    rejectFriendship: (Friendship) -> Unit,
    addFriendshipState: AddFriendshipState,
    getUserByEmail: (String) -> Unit,
    sendFriendshipRequest: (User) -> Unit,
    resetAddFriendshipState: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState()

    var showAddFriendship by rememberSaveable { mutableStateOf(false) }
    var showFriendships by rememberSaveable { mutableStateOf(true) }

    if (showAddFriendship) {
        AddFriendshipScreen(
            sheetState = modalBottomSheetState,
            onModalBottomSheetDismiss = {
                showAddFriendship = false
                resetAddFriendshipState()
            },
            uiState = addFriendshipState,
            getUserByEmail = getUserByEmail,
            sendFriendshipRequest = sendFriendshipRequest,
        )
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(top = 20.dp, bottom = 18.dp),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.friendships),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
                VerticalSpacer(of = 22.dp)
                JCTabs(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    tabs = listOf(
                        stringResource(R.string.friendships),
                        stringResource(R.string.pending)
                    ),
                    onTabChange = {
                        if (it == 0) {
                            showFriendships = true
                            getFriendships()
                        } else {
                            showFriendships = false
                            getPendingFriendships()
                        }
                    },
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    indicatorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        showAddFriendship = true
                        modalBottomSheetState.show()
                    }
                },
            ) {
                Icon(
                    imageVector = Icons.Rounded.PersonAdd,
                    contentDescription = stringResource(R.string.add_friendship)
                )
            }
        }
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
            if (showFriendships) {
                FriendshipsScreen(
                    uiState = friendshipsState,
                    getFriendships = getFriendships,
                    deleteFriendship = deleteFriendship,
                )
            } else {
                PendingFriendshipsScreen(
                    uiState = pendingFriendshipsState,
                    getPendingFriendships = getPendingFriendships,
                    acceptFriendship = acceptFriendship,
                    rejectFriendship = rejectFriendship,
                )
            }
        }
    }
}
