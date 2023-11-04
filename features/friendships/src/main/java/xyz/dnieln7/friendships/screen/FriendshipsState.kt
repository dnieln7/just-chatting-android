package xyz.dnieln7.friendships.screen

import xyz.dnieln7.domain.model.Friendship

sealed class FriendshipsState {
    object Loading : FriendshipsState()
    class Error(val message: String) : FriendshipsState()
    class Success(val data: List<Friendship>) : FriendshipsState()
}

sealed class StatefulFriendship {
    object Loading : StatefulFriendship()
    class NotLoading(val friendship: Friendship) : StatefulFriendship()
}
