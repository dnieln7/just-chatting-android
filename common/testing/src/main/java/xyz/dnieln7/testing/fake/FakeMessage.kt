package xyz.dnieln7.testing.fake

import xyz.dnieln7.domain.model.Message

fun buildMessage(): Message {
    return Message(
        id = "1",
        chatID = "1",
        userID = "1",
        message = "Hello everyone!",
        createdAt = buildZonedDateTime(),
        updatedAt = buildZonedDateTime(),
    )
}

fun buildMessages(): List<Message> {
    return listOf(
        Message(
            id = "1",
            chatID = "1",
            userID = "1",
            message = "Hello everyone!",
            createdAt = buildZonedDateTime(),
            updatedAt = buildZonedDateTime(),
        ),
        Message(
            id = "2",
            chatID = "1",
            userID = "2",
            message = "Hi there!",
            createdAt = buildZonedDateTime(),
            updatedAt = buildZonedDateTime(),
        ),
        Message(
            id = "3",
            chatID = "2",
            userID = "3",
            message = "How are you doing?",
            createdAt = buildZonedDateTime(),
            updatedAt = buildZonedDateTime(),
        ),
    )
}
