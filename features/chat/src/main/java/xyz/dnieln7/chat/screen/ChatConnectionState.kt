package xyz.dnieln7.chat.screen

import xyz.dnieln7.domain.model.Message

data class ChatConnectionState(
    val status: ChatConnectionStatus = ChatConnectionStatus.NONE,
    val messages: List<Message> = emptyList(),
)

enum class ChatConnectionStatus {
    NONE,
    FETCHING_MESSAGES,
    MESSAGES_ERROR,
    CONNECTING,
    CONNECTION_ERROR,
    CONNECTED,
}

sealed class ChatConnectionState2 {
    object Connecting : ChatConnectionState2()
    object ConnectionError : ChatConnectionState2()
    object Connected : ChatConnectionState2()
    object FetchingMessages : ChatConnectionState2()
    object MessagesError : ChatConnectionState2()
    class Ready(val messages: List<Message>) : ChatConnectionState2()
}
