package xyz.dnieln7.friendships.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import xyz.dnieln7.domain.model.Chat
import xyz.dnieln7.friendships.screen.FriendshipsContainerScreen
import xyz.dnieln7.friendships.screen.FriendshipsContainerViewModel
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
            toggleBottomSheet = friendshipsContainerViewModel::toggleBottomSheet,
            toggleScreen = friendshipsContainerViewModel::toggleScreen,
            createChat = friendshipsContainerViewModel::createChat,
            resetChatState = friendshipsContainerViewModel::resetChatState,
            navigateToChat = navigateToChat,
            friendshipsState = friendshipsState,
            getFriendships = friendshipsViewModel::getFriendships,
            deleteFriendship = friendshipsViewModel::deleteFriendship,
            pendingFriendshipsState = pendingFriendshipsState,
            getPendingFriendships = pendingFriendshipsViewModel::getPendingFriendships,
            acceptFriendship = pendingFriendshipsViewModel::acceptFriendship,
            rejectFriendship = pendingFriendshipsViewModel::rejectFriendship,
            addFriendshipState = addFriendshipState,
            getUserByEmail = addFriendshipViewModel::getUserByEmail,
            sendFriendshipRequest = addFriendshipViewModel::sendFriendshipRequest,
            resetAddFriendshipState = addFriendshipViewModel::resetAddFriendshipState,
        )
    }
}

object FriendshipsDestination : NavDestination("just-chatting/friendships")
