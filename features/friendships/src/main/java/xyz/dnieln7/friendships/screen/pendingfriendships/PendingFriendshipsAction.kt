package xyz.dnieln7.friendships.screen.pendingfriendships

import xyz.dnieln7.domain.model.Friendship

sealed interface PendingFriendshipsAction {
    object OnRefreshPendingFriendshipsPull : PendingFriendshipsAction
    data class OnAcceptFriendship(val friendship: Friendship) : PendingFriendshipsAction
    data class OnRejectFriendship(val friendship: Friendship) : PendingFriendshipsAction
}