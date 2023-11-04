package xyz.dnieln7.friendships.screen

import xyz.dnieln7.domain.model.Friendship

sealed class FriendshipsState {
    object Loading : FriendshipsState()
    class Error(val message: String) : FriendshipsState()
    class Success(val data: List<StatefulFriendship>) : FriendshipsState()
}

data class StatefulFriendship(
    val isLoading: Boolean = false,
    val data: Friendship,
)
