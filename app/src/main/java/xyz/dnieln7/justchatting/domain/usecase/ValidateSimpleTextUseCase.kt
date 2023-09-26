package xyz.dnieln7.justchatting.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import xyz.dnieln7.justchatting.domain.validation.SimpleTextValidationError

class ValidateSimpleTextUseCase {

    operator fun invoke(email: String): Either<SimpleTextValidationError, Unit> {
        return if (email.isBlank()) {
            SimpleTextValidationError.EMPTY.left()
        } else {
            Unit.right()
        }
    }
}