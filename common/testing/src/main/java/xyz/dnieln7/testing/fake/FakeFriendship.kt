package xyz.dnieln7.testing.fake

import xyz.dnieln7.domain.model.Friendship

fun buildFriendship(): Friendship {
    return Friendship(
        id = "fake_id",
        email = "fake_email",
        username = "fake_username",
    )
}

fun buildFriendships(): List<Friendship> {
    return listOf(
        Friendship(
            id = "fake_id_1",
            email = "fake_email_1",
            username = "fake_username_1",
        ),
        Friendship(
            id = "fake_id_2",
            email = "fake_email_2",
            username = "fake_username_2",
        ),
        Friendship(
            id = "fake_id_3",
            email = "fake_email_3",
            username = "fake_username_3",
        ),
    )
}
