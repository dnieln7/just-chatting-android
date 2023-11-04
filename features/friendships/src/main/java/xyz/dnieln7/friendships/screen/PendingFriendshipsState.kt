package xyz.dnieln7.friendships.screen

import xyz.dnieln7.domain.model.Friendship

sealed class PendingFriendshipsState {
    object Loading : PendingFriendshipsState()
    class Error(val message: String) : PendingFriendshipsState()
    class Success(val data: List<StatefulPendingFriendship>) : PendingFriendshipsState()
}

data class StatefulPendingFriendship(
    val isLoading: Boolean = false,
    val data: Friendship,
)
