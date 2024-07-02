package xyz.dnieln7.profile.screen

sealed interface ProfileAction {
    object OnRetryClick : ProfileAction
    object OnLogoutClick : ProfileAction
    object OnLoggedOut : ProfileAction
}