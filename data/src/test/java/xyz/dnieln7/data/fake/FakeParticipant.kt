package xyz.dnieln7.data.fake

import xyz.dnieln7.data.server.model.ChatSvModel

fun buildParticipantSvModel(): ChatSvModel.ParticipantSvModel {
    return ChatSvModel.ParticipantSvModel(
        id = "fake_id",
        email = "fake_email",
        username = "fake_username",
    )
}