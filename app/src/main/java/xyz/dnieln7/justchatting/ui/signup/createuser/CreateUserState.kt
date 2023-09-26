package xyz.dnieln7.justchatting.ui.signup.createuser

import xyz.dnieln7.justchatting.domain.validation.EmailValidationError
import xyz.dnieln7.justchatting.domain.validation.SimpleTextValidationError

sealed class CreateUserState {
    object None : CreateUserState()
    object Success : CreateUserState()
    class Error(
        val emailError: EmailValidationError? = null,
        val usernameError: SimpleTextValidationError? = null,
    ) : CreateUserState()

    fun asError(): Error? {
        if (this is Error) {
            return this
        }

        return null
    }
}
