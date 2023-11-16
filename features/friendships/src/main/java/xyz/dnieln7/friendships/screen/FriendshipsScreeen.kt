package xyz.dnieln7.friendships.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Contacts
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.Pending
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material.icons.rounded.PersonSearch
import androidx.compose.material.icons.rounded.SearchOff
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import xyz.dnieln7.composable.alert.AlertAction
import xyz.dnieln7.composable.alert.JCAlert
import xyz.dnieln7.composable.alert.JCErrorAlert
import xyz.dnieln7.composable.button.JCButton
import xyz.dnieln7.composable.progress.JCProgressIndicator
import xyz.dnieln7.composable.pullrefresh.PullRefresh
import xyz.dnieln7.composable.spacer.VerticalSpacer
import xyz.dnieln7.composable.tab.JCTabs
import xyz.dnieln7.composable.textfield.JCOutlinedSearchTextField
import xyz.dnieln7.domain.model.Friendship
import xyz.dnieln7.domain.model.User
import xyz.dnieln7.friendships.R
import xyz.dnieln7.friendships.composable.FriendshipListTile
import xyz.dnieln7.friendships.composable.PendingFriendshipListTile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendshipsScreen(
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
        AddFriendship(
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
                Friendships(friendshipsState, getFriendships, deleteFriendship)
            } else {
                PendingFriendships(
                    uiState = pendingFriendshipsState,
                    getPendingFriendships = getPendingFriendships,
                    acceptFriendship = acceptFriendship,
                    rejectFriendship = rejectFriendship,
                )
            }
        }
    }
}

@Composable
fun Friendships(
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

@Composable
fun PendingFriendships(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFriendship(
    sheetState: SheetState,
    onModalBottomSheetDismiss: () -> Unit,
    uiState: AddFriendshipState,
    getUserByEmail: (String) -> Unit,
    sendFriendshipRequest: (User) -> Unit
) {
    ModalBottomSheet(
        modifier = Modifier,
        sheetState = sheetState,
        onDismissRequest = onModalBottomSheetDismiss,
    ) {
        JCOutlinedSearchTextField(
            modifier = Modifier.padding(horizontal = 12.dp),
            placeholder = stringResource(R.string.search_by_email),
            onSearch = { getUserByEmail(it) }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 40.dp,
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (uiState) {
                AddFriendshipState.None -> JCAlert(
                    icon = Icons.Rounded.PersonSearch,
                    text = stringResource(R.string.search_user_hint)
                )

                AddFriendshipState.Loading -> JCProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )

                is AddFriendshipState.GetUserError -> {
                    JCErrorAlert(
                        icon = Icons.Rounded.SearchOff,
                        error = uiState.message,
                    )
                }

                is AddFriendshipState.UserFound -> {
                    Text(text = uiState.user.username, style = MaterialTheme.typography.titleLarge)
                    VerticalSpacer(of = 4.dp)
                    Text(text = uiState.user.email, style = MaterialTheme.typography.bodyLarge)
                    VerticalSpacer(of = 12.dp)
                    JCButton(
                        text = stringResource(R.string.send_friendship_request),
                        onClick = { sendFriendshipRequest(uiState.user) },
                    )
                }

                is AddFriendshipState.SendFriendshipRequestError -> JCErrorAlert(
                    icon = Icons.Rounded.Error,
                    error = uiState.message,
                    alertAction = AlertAction(
                        text = stringResource(R.string.try_again),
                        onClick = { sendFriendshipRequest(uiState.user) },
                    ),
                )

                is AddFriendshipState.FriendshipRequestSent -> JCAlert(
                    icon = Icons.Rounded.Check,
                    text = stringResource(
                        R.string.friendship_request_sent,
                        uiState.user.username,
                    )
                )
            }
        }
    }
}
