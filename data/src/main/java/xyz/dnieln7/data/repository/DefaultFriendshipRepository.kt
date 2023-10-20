package xyz.dnieln7.data.repository

import arrow.core.Either
import arrow.core.left
import xyz.dnieln7.data.database.dao.FriendshipDao
import xyz.dnieln7.data.mapper.toDbModel
import xyz.dnieln7.data.mapper.toDomain
import xyz.dnieln7.data.server.JustChattingApiService
import xyz.dnieln7.domain.model.Friendship
import xyz.dnieln7.domain.repository.FriendshipRepository

class DefaultFriendshipRepository(
    private val justChattingApiService: JustChattingApiService,
    private val friendshipDao: FriendshipDao,
) : FriendshipRepository {

    override suspend fun getFriendships(userID: String): Either<Throwable, List<Friendship>> {
        return Either.catch {
            val result = justChattingApiService.getFriendships(userID).map { it.toDbModel() }

            friendshipDao.insertFriendships(result)
        }.fold(
            {
                it.left()
            },
            {
                Either.catch { friendshipDao.getFriendships().map { it.toDomain() } }
            }
        )
    }
}