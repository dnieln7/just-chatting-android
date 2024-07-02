package xyz.dnieln7.friendships.screen.addfriendship

import xyz.dnieln7.domain.model.User

sealed interface AddFriendshipAction {
    data class OnSearchClick(val email: String) : AddFriendshipAction
    data class OnSendFriendshipClick(val user: User) : AddFriendshipAction
}
