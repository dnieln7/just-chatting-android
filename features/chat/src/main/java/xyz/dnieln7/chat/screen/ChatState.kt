package xyz.dnieln7.chat.screen

import xyz.dnieln7.domain.model.Chat

sealed class ChatState {
    object None : ChatState()
    object Loading : ChatState()
    class Error(val message: String) : ChatState()
    class Success(val data: Chat) : ChatState()
}
