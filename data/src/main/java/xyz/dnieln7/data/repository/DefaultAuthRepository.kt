package xyz.dnieln7.data.repository

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.HttpError
import arrow.retrofit.adapter.either.networkhandling.IOError
import arrow.retrofit.adapter.either.networkhandling.UnexpectedCallError
import xyz.dnieln7.data.R
import xyz.dnieln7.data.exception.ApiException
import xyz.dnieln7.data.exception.EmailNotAvailableException
import xyz.dnieln7.data.mapper.toDomain
import xyz.dnieln7.data.server.JustChattingApiService
import xyz.dnieln7.data.server.extension.isConflict
import xyz.dnieln7.data.server.model.EmailAvailabilitySvModel
import xyz.dnieln7.data.server.model.LoginSvModel
import xyz.dnieln7.data.server.model.SignupSvModel
import xyz.dnieln7.domain.model.User
import xyz.dnieln7.domain.provider.ResourceProvider
import xyz.dnieln7.domain.repository.AuthRepository

class DefaultAuthRepository(
    private val justChattingApiService: JustChattingApiService,
    private val resourceProvider: ResourceProvider,
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

    override suspend fun getEmailAvailability(email: String): Either<Throwable, Unit> {
        val emailAvailabilitySvModel = EmailAvailabilitySvModel(email)

        return justChattingApiService.getEmailAvailability(emailAvailabilitySvModel).mapLeft {
            when (it) {
                is HttpError -> {
                    if (it.isConflict()) {
                        val message = resourceProvider.getString(R.string.email_not_available)

                        EmailNotAvailableException(message)
                    } else {
                        ApiException(it.message)
                    }
                }

                is IOError -> it.cause
                is UnexpectedCallError -> it.cause
            }
        }
    }
}
