package xyz.dnieln7.signup.screen.createpassword

import xyz.dnieln7.justchatting.domain.validation.PasswordsValidationError

sealed class CreatePasswordState {
    object None : CreatePasswordState()
    object Success : CreatePasswordState()
    class Error(val passwordError: PasswordsValidationError? = null) : CreatePasswordState()

    fun asError(): Error? {
        if (this is Error) {
            return this
        }

        return null
    }
}
