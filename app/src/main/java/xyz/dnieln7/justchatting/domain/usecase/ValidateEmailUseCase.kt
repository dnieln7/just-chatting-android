package xyz.dnieln7.justchatting.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import xyz.dnieln7.justchatting.domain.validation.EmailValidationError
import java.util.regex.Pattern
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {

    operator fun invoke(email: String): Either<EmailValidationError, Unit> {
        return if (email.isBlank()) {
            EmailValidationError.EMPTY.left()
        } else if (!emailPattern.matcher(email).matches()) {
            EmailValidationError.NOT_AN_EMAIL.left()
        } else {
            Unit.right()
        }
    }
}

private val emailPattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
