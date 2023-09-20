package xyz.dnieln7.justchatting.ui.login

sealed class LoginState {
    object None : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    class Error(val message: String) : LoginState()
}
