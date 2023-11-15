package xyz.dnieln7.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import xyz.dnieln7.domain.validation.MINIMUM_PASSWORD_LENGTH
import xyz.dnieln7.domain.validation.PasswordValidationError
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {

    operator fun invoke(password: String): Either<PasswordValidationError, Unit> {
        return when {
            password.isBlank() -> {
                PasswordValidationError.EMPTY.left()
            }

            password.length < MINIMUM_PASSWORD_LENGTH -> {
                PasswordValidationError.TOO_SHORT.left()
            }

            else -> {
                Unit.right()
            }
        }
    }
}