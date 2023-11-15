package xyz.dnieln7.friendships.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import xyz.dnieln7.friendships.screen.AddFriendshipViewModel
import xyz.dnieln7.friendships.screen.FriendshipsScreen
import xyz.dnieln7.friendships.screen.FriendshipsViewModel
import xyz.dnieln7.friendships.screen.PendingFriendshipsViewModel
import xyz.dnieln7.navigation.NavDestination

fun NavGraphBuilder.friendshipsNavigation() {
    composable(route = FriendshipsDestination.route) {
        val friendshipsViewModel = hiltViewModel<FriendshipsViewModel>()
        val pendingFriendshipsViewModel = hiltViewModel<PendingFriendshipsViewModel>()
        val addFriendshipViewModel = hiltViewModel<AddFriendshipViewModel>()

        val friendshipsState by friendshipsViewModel.state.collectAsStateWithLifecycle()
        val pendingFriendshipsState by pendingFriendshipsViewModel.state.collectAsStateWithLifecycle()
        val addFriendshipState by addFriendshipViewModel.state.collectAsStateWithLifecycle()

        FriendshipsScreen(
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
