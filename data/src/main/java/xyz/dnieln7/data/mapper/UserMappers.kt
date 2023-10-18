package xyz.dnieln7.data.mapper

import xyz.dnieln7.data.server.model.UserSvModel
import xyz.dnieln7.domain.model.User
import xyz.dnieln7.domain.time.buildZonedDateTimeFromString

fun UserSvModel.toDomain(): User {
    return User(
        id = id,
        email = email,
        username = username,
        createdAt = buildZonedDateTimeFromString(createdAt),
        updatedAt = buildZonedDateTimeFromString(updatedAt),
    )
}
