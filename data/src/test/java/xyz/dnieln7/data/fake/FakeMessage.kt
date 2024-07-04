package xyz.dnieln7.data.fake

import xyz.dnieln7.data.server.model.MessageSvModel

fun buildMessageSvModel(): MessageSvModel {
    return MessageSvModel(
        id = "fake_id",
        chatId = "fake_chat_id",
        userId = "fake_user_id",
        message = "fake_message",
        createdAt = "2023-09-09T20:05:48.363565Z",
        updatedAt = "2023-09-09T20:05:48.363565Z",
    )
}
