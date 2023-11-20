package xyz.dnieln7.testing.fake

import xyz.dnieln7.domain.model.Chat
import xyz.dnieln7.domain.model.Participant

fun buildChat(): Chat {
    return Chat(
        id = "fake_id",
        me = Participant(
            id = "fake_me_id",
            email = "fake_me_email",
            username = "fake_me_username",
        ),
        creator = Participant(
            id = "fake_creator_id",
            email = "fake_creator_email",
            username = "fake_creator_username",
        ),
        friend = Participant(
            id = "fake_friend_id",
            email = "fake_friend_email",
            username = "fake_friend_username",
        ),
        createdAt = buildZonedDateTime(),
        updatedAt = buildZonedDateTime(),
    )
}

fun buildChats(): List<Chat> {
    return listOf(
        Chat(
            id = "1",
            me = Participant(
                id = "1",
                email = "johndoe@example.com",
                username = "johndoe"
            ),
            creator = Participant(
                id = "2",
                email = "janedoe@example.com",
                username = "janedoe"
            ),
            friend = Participant(
                id = "3",
                email = "alex@example.com",
                username = "alex"
            ),
            createdAt = buildZonedDateTime(),
            updatedAt = buildZonedDateTime(),
        ),
        Chat(
            id = "2",
            me = Participant(
                id = "4",
                email = "ben@example.com",
                username = "ben"
            ),
            creator = Participant(
                id = "5",
                email = "charlie@example.com",
                username = "charlie"
            ),
            friend = Participant(
                id = "6",
                email = "david@example.com",
                username = "david"
            ),
            createdAt = buildZonedDateTime(),
            updatedAt = buildZonedDateTime(),
        ),
        Chat(
            id = "3",
            me = Participant(
                id = "7",
                email = "emily@example.com",
                username = "emily"
            ),
            creator = Participant(
                id = "8",
                email = "frank@example.com",
                username = "frank"
            ),
            friend = Participant(
                id = "9",
                email = "grace@example.com",
                username = "grace"
            ),
            createdAt = buildZonedDateTime(),
            updatedAt = buildZonedDateTime(),
        )
    )
}