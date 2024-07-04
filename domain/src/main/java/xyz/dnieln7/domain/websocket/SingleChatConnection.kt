package xyz.dnieln7.domain.websocket

import kotlinx.coroutines.flow.SharedFlow
import xyz.dnieln7.domain.model.Message

interface SingleChatConnection {
    val status: SharedFlow<WebSocketStatus>
    val events: SharedFlow<Message>

    fun connect(userID: String, chatID: String)
    fun sendMessage(message: String)
    fun disconnect()
}