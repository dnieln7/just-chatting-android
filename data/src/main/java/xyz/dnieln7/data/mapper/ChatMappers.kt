package xyz.dnieln7.data.mapper

import xyz.dnieln7.data.server.model.GetChatsSvModel
import xyz.dnieln7.domain.model.Chat
import xyz.dnieln7.domain.time.buildZonedDateTimeFromString

fun GetChatsSvModel.ChatSvModel.toDomain(): Chat {
    return Chat(
        id = id,
        me = me.toDomain(),
        creator = creator.toDomain(),
        friend = friend.toDomain(),
        createdAt = buildZonedDateTimeFromString(createdAt),
        updatedAt = buildZonedDateTimeFromString(updatedAt),
    )
}
