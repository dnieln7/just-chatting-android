package xyz.dnieln7.friendships.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.dnieln7.coroutines.di.IO
import xyz.dnieln7.domain.model.Friendship
import xyz.dnieln7.domain.usecase.DeleteFriendshipUseCase
import xyz.dnieln7.domain.usecase.GetFriendshipsUseCase
import javax.inject.Inject

@HiltViewModel
class FriendshipsViewModel @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher,
    private val getFriendshipsUseCase: GetFriendshipsUseCase,
    private val deleteFriendshipUseCase: DeleteFriendshipUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<FriendshipsState>(FriendshipsState.Loading)
    val state get() = _state.asStateFlow()

    private var statefulFriendships = emptyList<StatefulFriendship>()

    init {
        getFriendships()
    }

    fun getFriendships() {
        viewModelScope.launch(dispatcher) {
            _state.emit(FriendshipsState.Loading)

            getFriendshipsUseCase().fold(
                {
                    _state.emit(FriendshipsState.Error(it))
                },
                { friendships ->
                    statefulFriendships = friendships.map { StatefulFriendship(data = it) }

                    _state.emit(FriendshipsState.Success(statefulFriendships))
                }
            )
        }
    }

    fun deleteFriendship(friendship: Friendship) {
        viewModelScope.launch(dispatcher) {
            statefulFriendships = statefulFriendships.map {
                if (it.data.id == friendship.id) {
                    it.copy(isLoading = true)
                } else {
                    it
                }
            }

            _state.emit(FriendshipsState.Success(statefulFriendships))

            deleteFriendshipUseCase(friendship.id).fold(
                {
                    statefulFriendships = statefulFriendships.map {
                        if (it.data.id == friendship.id) {
                            it.copy(isLoading = false)
                        } else {
                            it
                        }
                    }

                    _state.emit(FriendshipsState.Success(statefulFriendships))
                },
                {
                    statefulFriendships = statefulFriendships.filterNot {
                        it.data.id == friendship.id
                    }

                    _state.emit(FriendshipsState.Success(statefulFriendships))
                }
            )
        }
    }
}