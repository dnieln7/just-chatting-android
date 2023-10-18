package xyz.dnieln7.login.screen

import xyz.dnieln7.composable.button.NLSButtonStatus

sealed class LoginState {
    object None : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    class Error(val message: String) : LoginState()

    fun toNLSStatus(): NLSButtonStatus {
        return when (this) {
            None -> NLSButtonStatus.NONE
            Loading -> NLSButtonStatus.LOADING
            Success -> NLSButtonStatus.SUCCESS
            is Error -> NLSButtonStatus.NONE
        }
    }
}
