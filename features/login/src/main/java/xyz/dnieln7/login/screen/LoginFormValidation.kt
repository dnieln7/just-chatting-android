package xyz.dnieln7.login.screen

import xyz.dnieln7.domain.validation.EmailValidationError

data class LoginFormValidation(
    val initialized: Boolean = false,
    val emailValidationError: EmailValidationError? = null,
) {

    fun isValid(): Boolean {
        return initialized && emailValidationError == null
    }
}
