package xyz.dnieln7.data.repository

import arrow.core.Either
import retrofit2.HttpException
import xyz.dnieln7.data.R
import xyz.dnieln7.data.exception.UserNotFoundException
import xyz.dnieln7.data.mapper.toDomain
import xyz.dnieln7.data.server.JustChattingApiService
import xyz.dnieln7.data.server.extension.isNotFound
import xyz.dnieln7.domain.model.User
import xyz.dnieln7.domain.provider.ResourceProvider
import xyz.dnieln7.domain.repository.UserRepository

class DefaultUserRepository(
    private val justChattingApiService: JustChattingApiService,
    private val resourceProvider: ResourceProvider,
) : UserRepository {

    override suspend fun getUserByEmail(email: String): Either<Throwable, User> {
        return Either.catch { justChattingApiService.getUserByEmail(email).toDomain() }.mapLeft {
            if (it is HttpException && it.isNotFound()) {
                val message = resourceProvider.getString(R.string.user_not_found)

                UserNotFoundException(message)
            } else {
                it
            }
        }
    }
}