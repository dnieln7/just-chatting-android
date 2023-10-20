package xyz.dnieln7.domain.repository

import arrow.core.Either
import xyz.dnieln7.domain.model.Friendship

interface FriendshipRepository {
    suspend fun getFriendships(userID: String): Either<Throwable, List<Friendship>>
}