package xyz.dnieln7.signup.screen.register

sealed interface RegisterAction {
    object OnRegisterClick : RegisterAction
    object OnUserRegistered : RegisterAction
}
