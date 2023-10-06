package xyz.dnieln7.justchatting.data.mapper

import xyz.dnieln7.justchatting.data.server.model.UserSvModel
import xyz.dnieln7.justchatting.domain.model.User
import xyz.dnieln7.justchatting.domain.time.buildZonedDateTimeFromString

fun UserSvModel.toDomain(): User {
    return User(
        id = id,
        email = email,
        username = username,
        createdAt = buildZonedDateTimeFromString(createdAt),
        updatedAt = buildZonedDateTimeFromString(updatedAt),
    )
}
