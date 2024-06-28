package xyz.dnieln7.login.screen

sealed interface LoginAction {
    object OnSignupClick : LoginAction
    data class OnEmailInput(val text: String) : LoginAction
    data class OnPasswordInput(val text: String) : LoginAction
    data class OnLoginClick(val email: String, val password: String) : LoginAction
    object OnLoggedIn : LoginAction
}
