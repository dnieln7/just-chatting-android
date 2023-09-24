package xyz.dnieln7.justchatting.ui.signup.register

sealed class RegisterState {
    object Loading : RegisterState()
    object Success : RegisterState()
    class Error(val message: String) : RegisterState()
}
