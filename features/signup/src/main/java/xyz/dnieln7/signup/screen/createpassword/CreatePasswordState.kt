package xyz.dnieln7.signup.screen.createpassword

import xyz.dnieln7.domain.validation.PasswordsValidationError

sealed class CreatePasswordState {
    object None : CreatePasswordState()
    class Success(
        val email: String,
        val password: String,
        val username: String,
    ) : CreatePasswordState()

    class Error(val passwordError: PasswordsValidationError? = null) : CreatePasswordState()

    fun asError(): Error? {
        if (this is Error) {
            return this
        }

        return null
    }
}
