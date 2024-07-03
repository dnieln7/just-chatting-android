package xyz.dnieln7.chats.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import xyz.dnieln7.chats.screen.ChatsAction
import xyz.dnieln7.chats.screen.ChatsScreen
import xyz.dnieln7.chats.screen.ChatsViewModel
import xyz.dnieln7.domain.model.Chat
import xyz.dnieln7.navigation.NavDestination

fun NavGraphBuilder.chatsNavigation(navigateToChat: (Chat) -> Unit) {
    composable(route = ChatsDestination.route) {
        val chatsViewModel = hiltViewModel<ChatsViewModel>()

        val uiState by chatsViewModel.state.collectAsStateWithLifecycle()

        ChatsScreen(
            uiState = uiState,
            onAction = {
                when (it) {
                    ChatsAction.OnRefreshChatsPull -> chatsViewModel.getChats()
                    is ChatsAction.OnChatClick -> navigateToChat(it.chat)
                }
            },
        )
    }
}

object ChatsDestination : NavDestination("just-chatting/chats")
