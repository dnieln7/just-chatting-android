package xyz.dnieln7.signup.screen.register

sealed interface RegisterAction {
    object OnRetryClick : RegisterAction
    object OnUserRegistered : RegisterAction
}
