package xyz.dnieln7.chats.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import xyz.dnieln7.chats.screen.ChatsScreen
import xyz.dnieln7.navigation.NavDestination

fun NavGraphBuilder.chatsNavigation() {
    composable(route = ChatsDestination.route) {
        ChatsScreen()
    }
}

object ChatsDestination : NavDestination("just-chatting/chats")
