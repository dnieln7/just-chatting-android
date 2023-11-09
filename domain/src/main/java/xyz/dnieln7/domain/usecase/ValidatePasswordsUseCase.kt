package xyz.dnieln7.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import xyz.dnieln7.domain.validation.PasswordsValidationError
import javax.inject.Inject

class ValidatePasswordsUseCase @Inject constructor() {

    operator fun invoke(
        password: String,
        password2: String,
    ): Either<PasswordsValidationError, Unit> {
        return when {
            password.isBlank() -> {
                PasswordsValidationError.EMPTY.left()
            }

            password.length < 8 -> {
                PasswordsValidationError.TOO_SHORT.left()
            }

            password != password2 -> {
                PasswordsValidationError.NOT_EQUAL.left()
            }

            else -> {
                Unit.right()
            }
        }
    }
}