package xyz.dnieln7.friendships.screen.friendships

import xyz.dnieln7.domain.model.Friendship

sealed interface FriendshipsAction {
    object OnRefreshFriendshipsPull : FriendshipsAction
    data class OnFriendshipClick(val friendship: Friendship) : FriendshipsAction
    data class OnDeleteFriendshipClick(val friendship: Friendship) : FriendshipsAction
}
