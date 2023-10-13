package xyz.dnieln7.justchatting.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import retrofit2.HttpException
import xyz.dnieln7.justchatting.R
import xyz.dnieln7.justchatting.data.server.extension.isConflict
import xyz.dnieln7.justchatting.domain.provider.ResourceProvider
import xyz.dnieln7.justchatting.domain.repository.AuthRepository
import javax.inject.Inject

class GetEmailAvailabilityUserCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val authRepository: AuthRepository,
    private val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase
) {

    suspend operator fun invoke(email: String): Either<String, Unit> {
        return authRepository.getEmailAvailability(email).fold(
            {
                if (it is HttpException && it.isConflict()) {
                    resourceProvider.getString(R.string.email_not_available).left()
                } else {
                    getErrorFromThrowableUseCase(it).left()
                }
            },
            {
                it.right()
            }
        )
    }
}
