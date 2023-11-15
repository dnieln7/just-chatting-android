package xyz.dnieln7.domain.repository

import arrow.core.Either
import xyz.dnieln7.domain.model.Friendship

interface FriendshipRepository {
    suspend fun sendFriendshipRequest(userID: String, friendID: String): Either<Throwable, Unit>
    suspend fun acceptFriendshipRequest(userID: String, friendID: String): Either<Throwable, Unit>
    suspend fun getPendingFriendships(userID: String): Either<Throwable, List<Friendship>>
    suspend fun getFriendships(userID: String): Either<Throwable, List<Friendship>>
    suspend fun deleteFriendship(userID: String, friendID: String): Either<Throwable, Unit>
}