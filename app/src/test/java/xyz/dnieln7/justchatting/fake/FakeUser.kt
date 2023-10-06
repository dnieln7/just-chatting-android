package xyz.dnieln7.justchatting.fake

import xyz.dnieln7.justchatting.domain.model.User

fun buildUser(): User {
    return User(
        id = "fake_id",
        email = "fake_email",
        username = "fake_username",
        createdAt = buildZonedDateTime(),
        updatedAt = buildZonedDateTime(),
    )
}
