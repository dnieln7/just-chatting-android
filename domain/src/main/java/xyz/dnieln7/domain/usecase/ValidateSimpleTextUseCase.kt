package xyz.dnieln7.domain.usecase

import xyz.dnieln7.domain.validation.ValidationStatus
import javax.inject.Inject

class ValidateSimpleTextUseCase @Inject constructor() {

    operator fun invoke(email: String): ValidationStatus.Text {
        return if (email.isBlank()) {
            ValidationStatus.Text.Invalid.EMPTY
        } else {
            ValidationStatus.Text.Valid
        }
    }
}
