package xyz.dnieln7.chat.screen

import xyz.dnieln7.data.websocket.model.WebSocketStatus
import xyz.dnieln7.domain.model.Chat
import xyz.dnieln7.domain.model.Message

data class ChatState(
    val status: WebSocketStatus = WebSocketStatus.DISCONNECTED,
    val loadingChat: Boolean = false,
    val chatError: String? = null,
    val chat: Chat? = null,
    val loadingMessages: Boolean = false,
    val messagesError: String? = null,
    val messages: List<Message> = emptyList(),
)
