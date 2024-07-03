package xyz.dnieln7.chat.screen

sealed interface ChatAction {
    object OnBackClick : ChatAction
    object OnLoadChatRetryClick : ChatAction
    object OnLoadMessagesRetryClick : ChatAction
    object OnReconnectClick : ChatAction
    data class OnSendMessageClick(val message: String) : ChatAction
}
