package xyz.dnieln7.friendships.screen.friendshipsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.dnieln7.coroutines.di.IO
import xyz.dnieln7.domain.model.User
import xyz.dnieln7.domain.usecase.GetUserByEmailUseCase
import xyz.dnieln7.domain.usecase.SendFriendshipRequestUseCase
import javax.inject.Inject

@HiltViewModel
class FriendshipSearchViewModel @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher,
    private val getUserByEmailUseCase: GetUserByEmailUseCase,
    private val sendFriendshipRequestUseCase: SendFriendshipRequestUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<FriendshipSearchState>(FriendshipSearchState.None)
    val state get() = _state.asStateFlow()

    fun getUserByEmail(email: String) {
        viewModelScope.launch(dispatcher) {
            _state.emit(FriendshipSearchState.Loading)

            getUserByEmailUseCase(email).fold(
                {
                    _state.emit(FriendshipSearchState.GetUserError(it))
                },
                {
                    _state.emit(FriendshipSearchState.UserFound(it))
                }
            )
        }
    }

    fun sendFriendshipRequest(user: User) {
        viewModelScope.launch(dispatcher) {
            _state.emit(FriendshipSearchState.Loading)

            sendFriendshipRequestUseCase(user.id).fold(
                {
                    _state.emit(FriendshipSearchState.SendFriendshipSearchRequestError(it, user))
                },
                {
                    _state.emit(FriendshipSearchState.FriendshipRequestSentSearch(user))
                }
            )
        }
    }

    fun resetAddFriendshipState() {
        viewModelScope.launch(dispatcher) {
            _state.emit(FriendshipSearchState.None)
        }
    }
}