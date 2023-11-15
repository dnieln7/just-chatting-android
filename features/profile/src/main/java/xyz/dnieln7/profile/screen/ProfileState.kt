package xyz.dnieln7.profile.screen

import xyz.dnieln7.domain.model.User

sealed class ProfileState {
    object Loading : ProfileState()
    object UserNotFound : ProfileState()
    class UserFound(val data: User) : ProfileState()
    class LogoutError(val message: String) : ProfileState()
    object LoggedOut : ProfileState()
}
