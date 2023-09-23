package xyz.dnieln7.justchatting.ui.signup

import xyz.dnieln7.justchatting.domain.validation.EmailValidationError
import xyz.dnieln7.justchatting.domain.validation.PasswordsValidationError
import xyz.dnieln7.justchatting.domain.validation.UsernameValidationError

sealed class SignupState {
    data class CreateUser(
        val emailValidationError: EmailValidationError? = null,
        val usernameValidationError: UsernameValidationError? = null,
    ) : SignupState()

    data class CreatePassword(val passwordError: PasswordsValidationError? = null) : SignupState()
    data class Register(val registerStatus: RegisterStatus) : SignupState()
}

sealed class RegisterStatus {
    object Registering : RegisterStatus()
    object Registered : RegisterStatus()
    class Error(val message: String) : RegisterStatus()
}
