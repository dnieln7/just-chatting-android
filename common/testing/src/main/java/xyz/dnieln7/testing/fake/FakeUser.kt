package xyz.dnieln7.testing.fake

import xyz.dnieln7.domain.model.User

fun buildUser(): User {
    return User(
        id = "fake_id",
        email = "fake_email",
        username = "fake_username",
        createdAt = buildZonedDateTime(),
        updatedAt = buildZonedDateTime(),
    )
}
