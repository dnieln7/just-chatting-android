package xyz.dnieln7.login.screen

import xyz.dnieln7.composable.button.StatefulButtonStatus

sealed class LoginState {
    object None : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    class Error(val message: String) : LoginState()

    fun toNLSStatus(): StatefulButtonStatus {
        return when (this) {
            None -> StatefulButtonStatus.NONE
            Loading -> StatefulButtonStatus.LOADING
            Success -> StatefulButtonStatus.SUCCESS
            is Error -> StatefulButtonStatus.NONE
        }
    }
}
