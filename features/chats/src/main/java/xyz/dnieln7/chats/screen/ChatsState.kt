package xyz.dnieln7.chats.screen

import xyz.dnieln7.domain.model.Chat

sealed class ChatsState {
    object Loading : ChatsState()
    class Error(val message: String) : ChatsState()
    class Success(val data: List<Chat>) : ChatsState()
}
