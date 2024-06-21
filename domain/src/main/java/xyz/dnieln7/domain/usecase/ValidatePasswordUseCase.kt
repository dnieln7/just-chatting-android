package xyz.dnieln7.domain.usecase

import xyz.dnieln7.domain.validation.MINIMUM_PASSWORD_LENGTH
import xyz.dnieln7.domain.validation.ValidationStatus
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {

    operator fun invoke(password: String): ValidationStatus.Password {
        return if (password.isBlank()) {
            ValidationStatus.Password.Invalid.EMPTY
        } else if (password.length < MINIMUM_PASSWORD_LENGTH) {
            ValidationStatus.Password.Invalid.TOO_SHORT
        } else {
            ValidationStatus.Password.Valid
        }
    }
}