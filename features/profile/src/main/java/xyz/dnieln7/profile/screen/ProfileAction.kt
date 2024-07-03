package xyz.dnieln7.profile.screen

sealed interface ProfileAction {
    object OnLoadProfileRetryClick : ProfileAction
    object OnLogoutClick : ProfileAction
    object OnLoggedOut : ProfileAction
}