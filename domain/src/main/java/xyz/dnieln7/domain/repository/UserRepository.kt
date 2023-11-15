package xyz.dnieln7.domain.repository

import arrow.core.Either
import xyz.dnieln7.domain.model.User

interface UserRepository {
    suspend fun getUserByEmail(email: String): Either<Throwable, User>
}