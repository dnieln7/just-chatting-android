package xyz.dnieln7.chat.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import xyz.dnieln7.chat.screen.ChatScreen
import xyz.dnieln7.chat.screen.ChatViewModel
import xyz.dnieln7.navigation.NavArgsDestination

fun NavGraphBuilder.chatNavigation(
    navigateBack: () -> Unit,
) {
    composable(route = ChatDestination.routeWithArgs, arguments = ChatDestination.args) {
        val chatViewModel = hiltViewModel<ChatViewModel>()

        val uiState by chatViewModel.state.collectAsStateWithLifecycle()

        ChatScreen(
            uiState = uiState,
            isMe = chatViewModel::isMe,
            getUsername = chatViewModel::getUsername,
            sendMessage = chatViewModel::sendMessage,
            navigateBack = navigateBack,
        )
    }
}

object ChatDestination : NavArgsDestination(
    route = "just-chatting/chat",
    args = listOf(
        navArgument(CHAT_USER_ID) { type = NavType.StringType },
        navArgument(CHAT_CHAT_ID) { type = NavType.StringType },
    ),
)

fun navigateToChat(navController: NavController, userID: String, chatID: String) {
    navController.navigate("${ChatDestination.route}/$userID/$chatID")
}

internal const val CHAT_USER_ID = "chat_user_id"
internal const val CHAT_CHAT_ID = "chat_chat_id"
