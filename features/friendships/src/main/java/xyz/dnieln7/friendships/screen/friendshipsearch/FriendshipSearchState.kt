package xyz.dnieln7.friendships.screen.friendshipsearch

import xyz.dnieln7.domain.model.User

sealed class FriendshipSearchState {
    object None : FriendshipSearchState()
    object Loading : FriendshipSearchState()
    class GetUserError(val message: String) : FriendshipSearchState()
    class UserFound(val user: User) : FriendshipSearchState()
    class FriendshipRequestSentSearch(val user: User) : FriendshipSearchState()
    class SendFriendshipSearchRequestError(val message: String, val user: User) : FriendshipSearchState()
}
