package xyz.dnieln7.justchatting.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import xyz.dnieln7.justchatting.domain.validation.EmailValidationError

class ValidateEmailUseCase {

    operator fun invoke(email: String): Either<Unit, EmailValidationError> {
        return if (email.isBlank()) {
            EmailValidationError.EMPTY.right()
        } else if (!email.contains("@")) {
            EmailValidationError.NOT_AN_EMAIL.right()
        } else {
            Unit.left()
        }
    }
}