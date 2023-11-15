package xyz.dnieln7.domain.usecase

import arrow.core.Either
import xyz.dnieln7.domain.repository.AuthRepository
import javax.inject.Inject

class GetEmailAvailabilityUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase
) {

    suspend operator fun invoke(email: String): Either<String, Unit> {
        return authRepository.getEmailAvailability(email).mapLeft {
            getErrorFromThrowableUseCase(it)
        }
    }
}
