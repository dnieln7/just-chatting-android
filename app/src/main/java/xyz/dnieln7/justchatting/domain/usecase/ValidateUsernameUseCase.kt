package xyz.dnieln7.justchatting.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import xyz.dnieln7.justchatting.domain.validation.UsernameValidationError

class ValidateUsernameUseCase {

    operator fun invoke(username: String): Either<Unit, UsernameValidationError> {
        return if (username.isBlank()) {
            UsernameValidationError.EMPTY.right()
        } else {
            Unit.left()
        }
    }
}