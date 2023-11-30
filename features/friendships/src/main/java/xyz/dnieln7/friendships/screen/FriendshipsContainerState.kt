package xyz.dnieln7.friendships.screen

import xyz.dnieln7.domain.model.Chat

data class FriendshipsContainerState(
    val currentScreen: FriendshipScreen = FriendshipScreen.FRIENDSHIPS,
    val showBottomSheet: Boolean = false,
    val creatingChat: Boolean = false,
    val createChatError: String? = null,
    val chat: Chat? = null,
)

enum class FriendshipScreen {
    FRIENDSHIPS,
    PENDING_FRIENDSHIPS,
}
