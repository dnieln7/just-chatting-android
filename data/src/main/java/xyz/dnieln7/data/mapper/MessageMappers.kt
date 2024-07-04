package xyz.dnieln7.data.mapper

import xyz.dnieln7.data.server.model.MessageSvModel
import xyz.dnieln7.domain.model.Message
import xyz.dnieln7.domain.time.buildZonedDateTimeFromString

fun MessageSvModel.toDomain(): Message {
    return Message(
        id = id,
        chatID = chatId,
        userID = userId,
        message = message,
        createdAt = buildZonedDateTimeFromString(createdAt),
        updatedAt = buildZonedDateTimeFromString(updatedAt),
    )
}
