package xyz.dnieln7.signup.screen.createpassword

import xyz.dnieln7.domain.validation.PasswordValidationError

data class CreatePasswordFormValidation(
    val passwordsMatch: Boolean? = null,
    val passwordValidationError: PasswordValidationError? = PasswordValidationError.EMPTY,
) {

    fun isValid(): Boolean {
        return passwordsMatch == true && passwordValidationError == null
    }
}
