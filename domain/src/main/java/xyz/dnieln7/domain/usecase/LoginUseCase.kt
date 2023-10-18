package xyz.dnieln7.domain.usecase

import arrow.core.Either
import arrow.core.raise.either
import xyz.dnieln7.domain.preferences.DataStorePreferences
import xyz.dnieln7.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val dataStorePreferences: DataStorePreferences,
    private val authRepository: AuthRepository,
    private val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase,
) {

    suspend operator fun invoke(email: String, password: String): Either<String, Unit> {
        return either {
            val user = authRepository.login(email, password).bind()

            dataStorePreferences.setUser(user)
        }.mapLeft {
            getErrorFromThrowableUseCase(it)
        }
    }
}
