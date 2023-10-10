package xyz.dnieln7.justchatting.domain.usecase

import arrow.core.Either
import arrow.core.raise.either
import xyz.dnieln7.justchatting.domain.provider.PreferencesProvider
import xyz.dnieln7.justchatting.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val preferencesProvider: PreferencesProvider,
    private val authRepository: AuthRepository,
    private val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase,
) {

    suspend operator fun invoke(email: String, password: String): Either<String, Unit> {
        return either {
            val user = authRepository.login(email, password).bind()

            preferencesProvider.setUser(user)
        }.mapLeft {
            getErrorFromThrowableUseCase(it)
        }
    }
}
