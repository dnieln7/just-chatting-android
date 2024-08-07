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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import xyz.dnieln7.composable.progress.JCProgressIndicator
import xyz.dnieln7.composable.spacer.VerticalSpacer
import xyz.dnieln7.composable.tab.JCTabs
import xyz.dnieln7.friendships.R
import xyz.dnieln7.friendships.screen.friendships.FriendshipsAction
import xyz.dnieln7.friendships.screen.friendships.FriendshipsScreen
import xyz.dnieln7.friendships.screen.friendships.FriendshipsState
import xyz.dnieln7.friendships.screen.friendshipsearch.FriendshipSearchAction
import xyz.dnieln7.friendships.screen.friendshipsearch.FriendshipSearchBottomSheet
import xyz.dnieln7.friendships.screen.friendshipsearch.FriendshipSearchState
import xyz.dnieln7.friendships.screen.pendingfriendships.PendingFriendshipsAction
import xyz.dnieln7.friendships.screen.pendingfriendships.PendingFriendshipsScreen
import xyz.dnieln7.friendships.screen.pendingfriendships.PendingFriendshipsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendshipsOverviewScreen(
    friendshipsContainerState: FriendshipsContainerState,
    friendshipsState: FriendshipsState,
    pendingFriendshipsState: PendingFriendshipsState,
    friendshipSearchState: FriendshipSearchState,
    onAction: (FriendshipsOverviewAction) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState()

    if (friendshipsContainerState.showBottomSheet) {
        FriendshipSearchBottomSheet(
            uiState = friendshipSearchState,
            sheetState = modalBottomSheetState,
            onAction = {
                when (it) {
                    is FriendshipSearchAction.OnSearchClick -> {
                        onAction(FriendshipsOverviewAction.OnSearchClick(it.email))
                    }

                    is FriendshipSearchAction.OnSendFriendshipClick -> {
                        onAction(FriendshipsOverviewAction.OnSendFriendshipClick(it.user))
                    }

                    FriendshipSearchAction.OnDismissClick -> scope.launch {
                        onAction(FriendshipsOverviewAction.OnDismissBottomSheetClick)
                        modalBottomSheetState.hide()
                    }
                }
            }
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
                if (!friendshipsContainerState.creatingChat) {
                    VerticalSpacer(of = 22.dp)
                    JCTabs(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        tabs = listOf(
                            stringResource(R.string.friendships),
                            stringResource(R.string.pending)
                        ),
                        onTabChange = {
                            if (it == 0) {
                                onAction(
                                    FriendshipsOverviewAction.OnTabClick(FriendshipScreen.FRIENDSHIPS)
                                )
                            } else {
                                onAction(
                                    FriendshipsOverviewAction.OnTabClick(
                                        FriendshipScreen.PENDING_FRIENDSHIPS
                                    )
                                )
                            }
                        },
                        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        indicatorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            }
        },
        floatingActionButton = {
            if (!friendshipsContainerState.creatingChat) {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            onAction(FriendshipsOverviewAction.OnShowBottomSheetClick)
                            modalBottomSheetState.show()
                        }
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Rounded.PersonAdd,
                            contentDescription = stringResource(R.string.add_friendship)
                        )
                    }
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
            if (friendshipsContainerState.creatingChat) {
                if (friendshipsContainerState.chat != null) {
                    LaunchedEffect(Unit) {
                        onAction(FriendshipsOverviewAction.OnChatCreated(friendshipsContainerState.chat))
                    }
                } else {
                    JCProgressIndicator(modifier = Modifier.fillMaxSize())
                }
            } else {
                FriendshipsTabs(
                    friendshipsContainerState = friendshipsContainerState,
                    friendshipsState = friendshipsState,
                    pendingFriendshipsState = pendingFriendshipsState,
                    onAction = onAction,
                )
            }
        }
    }
}

@Composable
private fun FriendshipsTabs(
    friendshipsContainerState: FriendshipsContainerState,
    friendshipsState: FriendshipsState,
    pendingFriendshipsState: PendingFriendshipsState,
    onAction: (FriendshipsOverviewAction) -> Unit,
) {
    when (friendshipsContainerState.currentScreen) {
        FriendshipScreen.FRIENDSHIPS -> FriendshipsScreen(
            uiState = friendshipsState,
            onAction = {
                when (it) {
                    FriendshipsAction.OnRefreshFriendshipsPull -> {
                        onAction(FriendshipsOverviewAction.OnRefreshPendingFriendshipsPull)
                    }

                    is FriendshipsAction.OnFriendshipClick -> {
                        onAction(FriendshipsOverviewAction.OnFriendshipClick(it.friendship))
                    }

                    is FriendshipsAction.OnDeleteFriendshipClick -> {
                        onAction(
                            FriendshipsOverviewAction.OnDeleteFriendshipClick(it.friendship)
                        )
                    }
                }
            },
        )

        FriendshipScreen.PENDING_FRIENDSHIPS -> PendingFriendshipsScreen(
            uiState = pendingFriendshipsState,
            onAction = {
                when (it) {
                    PendingFriendshipsAction.OnRefreshPendingFriendshipsPull -> {
                        onAction(FriendshipsOverviewAction.OnRefreshFriendshipsPull)
                    }

                    is PendingFriendshipsAction.OnAcceptFriendship -> {
                        onAction(
                            FriendshipsOverviewAction.OnAcceptFriendship(it.friendship)
                        )
                    }

                    is PendingFriendshipsAction.OnRejectFriendship -> {
                        onAction(
                            FriendshipsOverviewAction.OnRejectFriendship(it.friendship)
                        )
                    }
                }
            },
        )
    }
}
