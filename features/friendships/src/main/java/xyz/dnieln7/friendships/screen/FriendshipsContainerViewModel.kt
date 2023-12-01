package xyz.dnieln7.friendships.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.dnieln7.coroutines.di.IO
import xyz.dnieln7.domain.model.Friendship
import xyz.dnieln7.domain.usecase.CreateChatUseCase
import javax.inject.Inject

@HiltViewModel
class FriendshipsContainerViewModel @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher,
    private val createChatUseCase: CreateChatUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(FriendshipsContainerState())
    val state get() = _state.asStateFlow()

    fun toggleBottomSheet(show: Boolean) {
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(showBottomSheet = show) }
        }
    }

    fun toggleScreen(screen: FriendshipScreen) {
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(currentScreen = screen) }
        }
    }

    fun createChat(friendship: Friendship) {
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(creatingChat = true, createChatError = null, chat = null) }

            createChatUseCase(friendship.id).fold(
                { error ->
                    _state.update { it.copy(creatingChat = false, createChatError = error) }
                },
                { chat ->
                    _state.update { it.copy(creatingChat = false, chat = chat) }
                }
            )
        }
    }

    fun resetChatState() {
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(creatingChat = false, createChatError = null, chat = null) }
        }
    }
}