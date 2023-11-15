package xyz.dnieln7.domain.usecase

import arrow.core.Either
import xyz.dnieln7.domain.model.User
import xyz.dnieln7.domain.repository.UserRepository
import javax.inject.Inject

class GetUserByEmailUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase,
) {

    suspend operator fun invoke(email: String): Either<String, User> {
        return userRepository.getUserByEmail(email).mapLeft {
            getErrorFromThrowableUseCase(it)
        }
    }
}