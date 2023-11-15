package xyz.dnieln7.domain.repository

import arrow.core.Either
import xyz.dnieln7.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Either<Throwable, User>
    suspend fun signup(email: String, password: String, username: String): Either<Throwable, User>
    suspend fun getEmailAvailability(email: String): Either<Throwable, Unit>
}
