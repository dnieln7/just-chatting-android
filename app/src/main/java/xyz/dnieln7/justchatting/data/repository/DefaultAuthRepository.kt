package xyz.dnieln7.justchatting.data.repository

import arrow.core.Either
import xyz.dnieln7.justchatting.data.mapper.toDomain
import xyz.dnieln7.justchatting.data.server.JustChattingApiService
import xyz.dnieln7.justchatting.data.server.model.LoginSvModel
import xyz.dnieln7.justchatting.data.server.model.SignupSvModel
import xyz.dnieln7.justchatting.domain.model.User
import xyz.dnieln7.justchatting.domain.repository.AuthRepository

class DefaultAuthRepository(
    private val justChattingApiService: JustChattingApiService,
) : AuthRepository {

    override suspend fun login(email: String, password: String): Either<Throwable, User> {
        val loginSvModel = LoginSvModel(email, password)

        return Either.catch { justChattingApiService.login(loginSvModel).toDomain() }
    }

    override suspend fun signup(
        email: String,
        password: String,
        username: String
    ): Either<Throwable, User> {
        val signupSvModel = SignupSvModel(email, password, username)

        return Either.catch { justChattingApiService.signup(signupSvModel).toDomain() }
    }
}