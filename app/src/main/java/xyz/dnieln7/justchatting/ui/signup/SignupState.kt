package xyz.dnieln7.justchatting.ui.signup

sealed class SignupState {
    object Loading : SignupState()
    object Success : SignupState()
    class Error(val message: String) : SignupState()
}
