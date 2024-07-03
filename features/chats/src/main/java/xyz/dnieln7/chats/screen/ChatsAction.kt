package xyz.dnieln7.chats.screen

import xyz.dnieln7.domain.model.Chat

sealed interface ChatsAction {
    object OnRefreshChatsPull : ChatsAction
    data class OnChatClick(val chat: Chat) : ChatsAction
}
