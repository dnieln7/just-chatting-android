package xyz.dnieln7.justchatting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import xyz.dnieln7.friendships.navigation.FriendshipsDestination
import xyz.dnieln7.friendships.navigation.friendshipsNavigation
import xyz.dnieln7.navigation.NavDestination

fun NavGraphBuilder.homeNavHost(
    navController: NavController,
) {
    navigation(startDestination = FriendshipsDestination.route, route = HomeDestination.route) {
        friendshipsNavigation()
    }
}

object HomeDestination : NavDestination("just-chatting/home")
