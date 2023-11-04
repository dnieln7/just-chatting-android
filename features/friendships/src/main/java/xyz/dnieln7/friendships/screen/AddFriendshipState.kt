package xyz.dnieln7.friendships.screen

import xyz.dnieln7.domain.model.User

sealed class AddFriendshipState {
    object None : AddFriendshipState()
    object Loading : AddFriendshipState()
    class GetUserError(val message: String) : AddFriendshipState()
    class UserFound(val user: User) : AddFriendshipState()
    class FriendshipRequestSent(val user: User) : AddFriendshipState()
    class SendFriendshipRequestError(val message: String, val user: User) : AddFriendshipState()
}
