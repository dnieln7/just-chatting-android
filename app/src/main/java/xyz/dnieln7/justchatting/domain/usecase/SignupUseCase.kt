package xyz.dnieln7.justchatting.domain.usecase

import arrow.core.Either
import arrow.core.raise.either
import xyz.dnieln7.justchatting.domain.provider.PreferencesProvider
import xyz.dnieln7.justchatting.domain.repository.AuthRepository
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val preferencesProvider: PreferencesProvider,
    private val authRepository: AuthRepository,
    private val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase,
) {

    suspend operator fun invoke(
        email: String,
        password: String,
        username: String
    ): Either<String, Unit> {
        return either {
            val user = authRepository.signup(email, password, username).bind()

            preferencesProvider.setUser(user)
        }.mapLeft {
            getErrorFromThrowableUseCase(it)
        }
    }
}