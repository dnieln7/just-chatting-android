package xyz.dnieln7.profile.screen

sealed interface ProfileAction {
    object OnLoadProfileClick : ProfileAction
    object OnLogoutClick : ProfileAction
    object OnLoggedOut : ProfileAction
}