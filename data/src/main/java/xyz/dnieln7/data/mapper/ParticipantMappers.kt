package xyz.dnieln7.data.mapper

import xyz.dnieln7.data.server.model.ChatSvModel
import xyz.dnieln7.domain.model.Participant

fun ChatSvModel.ParticipantSvModel.toDomain(): Participant {
    return Participant(
        id = id,
        email = email,
        username = username,
    )
}
