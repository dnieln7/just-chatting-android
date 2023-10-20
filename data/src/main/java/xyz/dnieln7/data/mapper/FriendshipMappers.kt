package xyz.dnieln7.data.mapper

import xyz.dnieln7.data.database.model.FriendshipDbModel
import xyz.dnieln7.data.server.model.FriendshipSvModel
import xyz.dnieln7.domain.model.Friendship

fun FriendshipSvModel.toDbModel(): FriendshipDbModel {
    return FriendshipDbModel(
        id = id,
        email = email,
        username = username,
    )
}

fun FriendshipDbModel.toDomain(): Friendship {
    return Friendship(
        id = id,
        email = email,
        username = username,
    )
}
