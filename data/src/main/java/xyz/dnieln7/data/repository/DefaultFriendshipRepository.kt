package xyz.dnieln7.data.repository

import arrow.core.Either
import retrofit2.HttpException
import xyz.dnieln7.data.R
import xyz.dnieln7.data.database.dao.FriendshipDao
import xyz.dnieln7.data.exception.FriendshipDuplicatedException
import xyz.dnieln7.data.mapper.toDomain
import xyz.dnieln7.data.server.JustChattingApiService
import xyz.dnieln7.data.server.extension.isConflict
import xyz.dnieln7.data.server.model.AcceptFriendshipRequestSvModel
import xyz.dnieln7.data.server.model.SendFriendshipRequestSvModel
import xyz.dnieln7.domain.model.Friendship
import xyz.dnieln7.domain.provider.ResourceProvider
import xyz.dnieln7.domain.repository.FriendshipRepository

class DefaultFriendshipRepository(
    private val justChattingApiService: JustChattingApiService,
    private val friendshipDao: FriendshipDao,
    private val resourceProvider: ResourceProvider,
) : FriendshipRepository {

    override suspend fun sendFriendshipRequest(
        userID: String,
        friendID: String
    ): Either<Throwable, Unit> {
        val sendFriendshipRequestSvModel = SendFriendshipRequestSvModel(userID, friendID)

        return Either.catch {
            justChattingApiService.sendFriendshipRequest(sendFriendshipRequestSvModel)
        }.mapLeft {
            if (it is HttpException && it.isConflict()) {
                val message = resourceProvider.getString(R.string.friendship_request_duplicated)

                FriendshipDuplicatedException(message)
            } else {
                it
            }
        }
    }

    override suspend fun acceptFriendshipRequest(
        userID: String,
        friendID: String
    ): Either<Throwable, Unit> {
        val acceptFriendshipRequestSvModel = AcceptFriendshipRequestSvModel(userID, friendID)

        return Either.catch {
            justChattingApiService.acceptFriendshipRequest(acceptFriendshipRequestSvModel)
        }
    }

    override suspend fun getPendingFriendships(userID: String): Either<Throwable, List<Friendship>> {
        return Either.catch {
            justChattingApiService.getPendingFriendships(userID).map { it.toDomain() }
        }
    }

    override suspend fun getFriendships(userID: String): Either<Throwable, List<Friendship>> {
        return Either.catch { justChattingApiService.getFriendships(userID).map { it.toDomain() } }
    }

    override suspend fun deleteFriendship(
        userID: String,
        friendID: String
    ): Either<Throwable, Unit> {
        return Either.catch { justChattingApiService.deleteFriendship(userID, friendID) }
    }
}