package xyz.dnieln7.friendships.screen

import xyz.dnieln7.domain.model.Chat
import xyz.dnieln7.domain.model.Friendship
import xyz.dnieln7.domain.model.User

sealed interface FriendshipsOverviewAction {
    object OnRefreshPendingFriendshipsPull : FriendshipsOverviewAction
    data class OnFriendshipClick(val friendship: Friendship) : FriendshipsOverviewAction
    data class OnDeleteFriendshipClick(val friendship: Friendship) : FriendshipsOverviewAction
    object OnRefreshFriendshipsPull : FriendshipsOverviewAction
    data class OnAcceptFriendship(val friendship: Friendship) : FriendshipsOverviewAction
    data class OnRejectFriendship(val friendship: Friendship) : FriendshipsOverviewAction
    data class OnSearchClick(val email: String) : FriendshipsOverviewAction
    data class OnSendFriendshipClick(val user: User) : FriendshipsOverviewAction
    data class OnChatCreated(val chat: Chat) : FriendshipsOverviewAction
    object OnShowBottomSheetClick : FriendshipsOverviewAction
    object OnDismissBottomSheetClick : FriendshipsOverviewAction
    data class OnTabClick(val friendshipScreen: FriendshipScreen) : FriendshipsOverviewAction
}
