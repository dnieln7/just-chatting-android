package xyz.dnieln7.justchatting.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import xyz.dnieln7.justchatting.domain.validation.PasswordsValidationError

class ValidatePasswordsUseCase {

    operator fun invoke(
        password: String,
        password2: String,
    ): Either<Unit, PasswordsValidationError> {
        return when {
            password.isBlank() -> {
                PasswordsValidationError.EMPTY.right()
            }

            password.length < 12 -> {
                PasswordsValidationError.LENGTH_LESS_THAN_12.right()
            }

            password != password2 -> {
                PasswordsValidationError.NOT_EQUAL.right()
            }

            else -> {
                Unit.left()
            }
        }
    }
}