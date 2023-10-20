package xyz.dnieln7.friendships.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import xyz.dnieln7.friendships.screen.FriendshipsRoute
import xyz.dnieln7.navigation.NavDestination

fun NavGraphBuilder.friendshipsNavigation() {
    composable(route = FriendshipsDestination.route) {
        FriendshipsRoute()
    }
}

object FriendshipsDestination : NavDestination("just-chatting/friendships")
