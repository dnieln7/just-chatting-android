package xyz.dnieln7.friendships.screen.friendshipsearch

import xyz.dnieln7.domain.model.User

sealed interface FriendshipSearchAction {
    data class OnSearchClick(val email: String) : FriendshipSearchAction
    data class OnSendFriendshipClick(val user: User) : FriendshipSearchAction
    object OnDismissClick : FriendshipSearchAction
}
