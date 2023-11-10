package xyz.dnieln7.signup.screen.createuser

import xyz.dnieln7.composable.button.NLSButtonStatus

sealed class CreateUserState {
    object None : CreateUserState()
    object Loading : CreateUserState()
    class Success(val email: String, val username: String) : CreateUserState()
    class Error(val message: String) : CreateUserState()

    fun toNLSStatus(): NLSButtonStatus {
        return when (this) {
            None -> NLSButtonStatus.NONE
            Loading -> NLSButtonStatus.LOADING
            is Success -> NLSButtonStatus.SUCCESS
            is Error -> NLSButtonStatus.NONE
        }
    }
}
