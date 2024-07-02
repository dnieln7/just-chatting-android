package xyz.dnieln7.friendships.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import xyz.dnieln7.domain.model.Chat
import xyz.dnieln7.friendships.screen.FriendshipScreen
import xyz.dnieln7.friendships.screen.FriendshipsContainerScreen
import xyz.dnieln7.friendships.screen.FriendshipsContainerViewModel
import xyz.dnieln7.friendships.screen.FriendshipsOverviewAction
import xyz.dnieln7.friendships.screen.addfriendship.AddFriendshipViewModel
import xyz.dnieln7.friendships.screen.friendships.FriendshipsViewModel
import xyz.dnieln7.friendships.screen.pendingfriendships.PendingFriendshipsViewModel
import xyz.dnieln7.navigation.NavDestination

fun NavGraphBuilder.friendshipsNavigation(navigateToChat: (Chat) -> Unit) {
    composable(route = FriendshipsDestination.route) {
        val friendshipsContainerViewModel = hiltViewModel<FriendshipsContainerViewModel>()
        val friendshipsViewModel = hiltViewModel<FriendshipsViewModel>()
        val pendingFriendshipsViewModel = hiltViewModel<PendingFriendshipsViewModel>()
        val addFriendshipViewModel = hiltViewModel<AddFriendshipViewModel>()

        val friendshipsContainerState by friendshipsContainerViewModel.state.collectAsStateWithLifecycle()
        val friendshipsState by friendshipsViewModel.state.collectAsStateWithLifecycle()
        val pendingFriendshipsState by pendingFriendshipsViewModel.state.collectAsStateWithLifecycle()
        val addFriendshipState by addFriendshipViewModel.state.collectAsStateWithLifecycle()

        FriendshipsContainerScreen(
            friendshipsContainerState = friendshipsContainerState,
            friendshipsState = friendshipsState,
            pendingFriendshipsState = pendingFriendshipsState,
            addFriendshipState = addFriendshipState,
            onAction = {
                when (it) {
                    FriendshipsOverviewAction.OnRefreshPendingFriendshipsPull -> {
                        friendshipsViewModel.getFriendships()
                    }

                    is FriendshipsOverviewAction.OnFriendshipClick -> {
                        friendshipsContainerViewModel.createChat(it.friendship)
                    }

                    is FriendshipsOverviewAction.OnDeleteFriendshipClick -> {
                        friendshipsViewModel.deleteFriendship(it.friendship)
                    }

                    FriendshipsOverviewAction.OnRefreshFriendshipsPull -> {
                        pendingFriendshipsViewModel.getPendingFriendships()
                    }

                    is FriendshipsOverviewAction.OnAcceptFriendship -> {
                        pendingFriendshipsViewModel.acceptFriendship(it.friendship)
                    }

                    is FriendshipsOverviewAction.OnRejectFriendship -> {
                        pendingFriendshipsViewModel.rejectFriendship(it.friendship)
                    }

                    is FriendshipsOverviewAction.OnSearchClick -> {
                        addFriendshipViewModel.getUserByEmail(it.email)
                    }

                    is FriendshipsOverviewAction.OnSendFriendshipClick -> {
                        addFriendshipViewModel.sendFriendshipRequest(it.user)
                    }

                    is FriendshipsOverviewAction.OnChatCreated -> {
                        navigateToChat(it.chat)
                        friendshipsContainerViewModel.resetState()
                    }

                    FriendshipsOverviewAction.OnShowBottomSheetClick -> {
                        friendshipsContainerViewModel.toggleBottomSheet(true)
                    }

                    FriendshipsOverviewAction.OnDismissBottomSheetClick -> {
                        friendshipsContainerViewModel.toggleBottomSheet(false)
                        addFriendshipViewModel.resetAddFriendshipState()
                    }

                    is FriendshipsOverviewAction.OnTabClick -> {
                        when (it.friendshipScreen) {
                            FriendshipScreen.FRIENDSHIPS -> {
                                friendshipsViewModel.getFriendships()
                            }

                            FriendshipScreen.PENDING_FRIENDSHIPS -> {
                                pendingFriendshipsViewModel.getPendingFriendships()
                            }
                        }

                        friendshipsContainerViewModel.toggleScreen(it.friendshipScreen)
                    }
                }
            }
        )
    }
}

object FriendshipsDestination : NavDestination("just-chatting/friendships")
