package xyz.dnieln7.signup.screen.register

sealed interface RegisterAction {
    object OnRegisterRetryClick : RegisterAction
    object OnUserRegistered : RegisterAction
}
