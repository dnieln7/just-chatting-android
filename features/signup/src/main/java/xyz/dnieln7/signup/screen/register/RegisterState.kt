package xyz.dnieln7.signup.screen.register

sealed class RegisterState {
    object Loading : RegisterState()
    object Success : RegisterState()
    class Error(val message: String) : RegisterState()
}
