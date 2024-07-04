package xyz.dnieln7.domain.usecase

import xyz.dnieln7.domain.validation.ValidationStatus
import java.util.regex.Pattern
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {

    operator fun invoke(email: String): ValidationStatus.Email {
        return if (email.isBlank()) {
            ValidationStatus.Email.Invalid.EMPTY
        } else if (!emailPattern.matcher(email).matches()) {
            ValidationStatus.Email.Invalid.MALFORMED
        } else {
            ValidationStatus.Email.Valid
        }
    }
}

private val emailPattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
