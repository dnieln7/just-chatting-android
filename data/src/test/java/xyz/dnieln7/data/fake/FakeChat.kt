package xyz.dnieln7.data.fake

import xyz.dnieln7.data.server.model.GetChatsSvModel

fun buildChatSvModel(): GetChatsSvModel.ChatSvModel {
    return GetChatsSvModel.ChatSvModel(
        id = "3",
        me = GetChatsSvModel.ChatSvModel.ParticipantSvModel(
            id = "fake_me_id",
            email = "fake_me_email",
            username = "fake_me_username",
        ),
        creator = GetChatsSvModel.ChatSvModel.ParticipantSvModel(
            id = "fake_creator_id",
            email = "fake_creator_email",
            username = "fake_creator_username",
        ),
        friend = GetChatsSvModel.ChatSvModel.ParticipantSvModel(
            id = "fake_friend_id",
            email = "fake_friend_email",
            username = "fake_friend_username",
        ),
        createdAt = "2023-09-09T20:05:48.363565Z",
        updatedAt = "2023-09-09T20:05:48.363565Z",
    )
}