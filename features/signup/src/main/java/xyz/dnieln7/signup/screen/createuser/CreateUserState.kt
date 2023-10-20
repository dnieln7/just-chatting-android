package xyz.dnieln7.signup.screen.createuser

import xyz.dnieln7.composable.button.NLSButtonStatus
import xyz.dnieln7.justchatting.domain.validation.EmailValidationError
import xyz.dnieln7.justchatting.domain.validation.SimpleTextValidationError

sealed class CreateUserState {
    object None : CreateUserState()
    object Loading : CreateUserState()
    object Success : CreateUserState()
    class Error(
        val error: String? = null,
        val emailError: EmailValidationError? = null,
        val usernameError: SimpleTextValidationError? = null,
    ) : CreateUserState()

    fun asError(): Error? {
        if (this is Error) {
            return this
        }

        return null
    }

    fun toNLSStatus(): NLSButtonStatus {
        return when (this) {
            None -> NLSButtonStatus.NONE
            Loading -> NLSButtonStatus.LOADING
            Success -> NLSButtonStatus.SUCCESS
            is Error -> NLSButtonStatus.NONE
        }
    }
}