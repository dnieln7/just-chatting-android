package xyz.dnieln7.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import xyz.dnieln7.domain.validation.SimpleTextValidationError
import javax.inject.Inject

class ValidateSimpleTextUseCase @Inject constructor() {

    operator fun invoke(email: String): Either<SimpleTextValidationError, Unit> {
        return if (email.isBlank()) {
            SimpleTextValidationError.EMPTY.left()
        } else {
            Unit.right()
        }
    }
}