package xyz.dnieln7.login.screen

import xyz.dnieln7.domain.validation.ValidationStatus

data class LoginFormValidation(
    val initialized: Boolean = false,
    val emailValidation: ValidationStatus.Email? = null,
) {

    fun isValid(): Boolean {
        return initialized && emailValidation == ValidationStatus.Email.Valid
    }
}
