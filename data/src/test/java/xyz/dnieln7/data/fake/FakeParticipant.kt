package xyz.dnieln7.data.fake

import xyz.dnieln7.data.server.model.GetChatsSvModel

fun buildParticipantSvModel(): GetChatsSvModel.ChatSvModel.ParticipantSvModel {
    return GetChatsSvModel.ChatSvModel.ParticipantSvModel(
        id = "fake_id",
        email = "fake_email",
        username = "fake_username",
    )
}