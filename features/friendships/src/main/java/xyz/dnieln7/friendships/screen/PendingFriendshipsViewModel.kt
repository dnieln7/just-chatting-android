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
import xyz.dnieln7.domain.usecase.AcceptFriendshipRequestUseCase
import xyz.dnieln7.domain.usecase.DeleteFriendshipUseCase
import xyz.dnieln7.domain.usecase.GetPendingFriendshipsUseCase
import javax.inject.Inject

@HiltViewModel
class PendingFriendshipsViewModel @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher,
    private val getPendingFriendshipsUseCase: GetPendingFriendshipsUseCase,
    private val acceptFriendshipRequestUseCase: AcceptFriendshipRequestUseCase,
    private val deleteFriendshipUseCase: DeleteFriendshipUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<PendingFriendshipsState>(PendingFriendshipsState.Loading)
    val state get() = _state.asStateFlow()

    private var statefulPendingFriendships = emptyList<StatefulPendingFriendship>()

    init {
        getPendingFriendships()
    }

    fun getPendingFriendships() {
        viewModelScope.launch(dispatcher) {
            _state.emit(PendingFriendshipsState.Loading)

            getPendingFriendshipsUseCase().fold(
                {
                    _state.emit(PendingFriendshipsState.Error(it))
                },
                { friendships ->
                    statefulPendingFriendships = friendships.map {
                        StatefulPendingFriendship(data = it)
                    }
                    _state.emit(PendingFriendshipsState.Success(statefulPendingFriendships))
                }
            )
        }
    }

    fun acceptFriendship(friendship: Friendship) {
        viewModelScope.launch(dispatcher) {
            statefulPendingFriendships = statefulPendingFriendships.map {
                if (it.data.id == friendship.id) {
                    it.copy(isLoading = true)
                } else {
                    it
                }
            }

            _state.emit(PendingFriendshipsState.Success(statefulPendingFriendships))

            acceptFriendshipRequestUseCase(friendship.id).fold(
                {
                    statefulPendingFriendships = statefulPendingFriendships.map {
                        if (it.data.id == friendship.id) {
                            it.copy(isLoading = false)
                        } else {
                            it
                        }
                    }

                    _state.emit(PendingFriendshipsState.Success(statefulPendingFriendships))
                },
                {
                    statefulPendingFriendships = statefulPendingFriendships.filterNot {
                        it.data.id == friendship.id
                    }

                    _state.emit(PendingFriendshipsState.Success(statefulPendingFriendships))
                }
            )
        }
    }

    fun rejectFriendship(friendship: Friendship) {
        viewModelScope.launch(dispatcher) {
            statefulPendingFriendships = statefulPendingFriendships.map {
                if (it.data.id == friendship.id) {
                    it.copy(isLoading = true)
                } else {
                    it
                }
            }

            _state.emit(PendingFriendshipsState.Success(statefulPendingFriendships))

            deleteFriendshipUseCase(friendship.id).fold(
                {
                    statefulPendingFriendships = statefulPendingFriendships.map {
                        if (it.data.id == friendship.id) {
                            it.copy(isLoading = false)
                        } else {
                            it
                        }
                    }

                    _state.emit(PendingFriendshipsState.Success(statefulPendingFriendships))
                },
                {
                    statefulPendingFriendships = statefulPendingFriendships.filterNot {
                        it.data.id == friendship.id
                    }

                    _state.emit(PendingFriendshipsState.Success(statefulPendingFriendships))
                }
            )
        }
    }
}