package xyz.dnieln7.justchatting.domain.repository

import arrow.core.Either
import xyz.dnieln7.justchatting.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Either<Throwable, User>
}
