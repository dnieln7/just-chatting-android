package xyz.dnieln7.data.mapper

import xyz.dnieln7.data.server.model.GetChatsSvModel
import xyz.dnieln7.domain.model.Participant

fun GetChatsSvModel.ChatSvModel.ParticipantSvModel.toDomain(): Participant {
    return Participant(
        id = id,
        email = email,
        username = username,
    )
}
