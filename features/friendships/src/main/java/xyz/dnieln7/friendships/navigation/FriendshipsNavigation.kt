package xyz.dnieln7.friendships.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import xyz.dnieln7.friendships.screen.FriendshipsScreen
import xyz.dnieln7.navigation.NavDestination

fun NavGraphBuilder.friendshipsNavigation() {
    composable(route = FriendshipsDestination.route) {
        FriendshipsScreen()
    }
}

object FriendshipsDestination : NavDestination("just-chatting/friendships")
