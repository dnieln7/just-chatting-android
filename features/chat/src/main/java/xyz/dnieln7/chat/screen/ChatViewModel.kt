package xyz.dnieln7.chat.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.dnieln7.chat.navigation.CHAT_CHAT_ID
import xyz.dnieln7.chat.navigation.CHAT_USER_ID
import xyz.dnieln7.coroutines.di.IO
import xyz.dnieln7.domain.usecase.GetChatUseCase
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle,
    private val getChatUseCase: GetChatUseCase,
) : ViewModel() {

    private val userID: String = savedStateHandle[CHAT_USER_ID]!!
    private val chatID: String = savedStateHandle[CHAT_CHAT_ID]!!

    private val _state = MutableStateFlow<ChatState>(ChatState.None)
    val state get() = _state.asStateFlow()

    init {
        getChat()
    }

    fun getChat() {
        viewModelScope.launch(dispatcher) {
            _state.emit(ChatState.Loading)

            getChatUseCase(userID, chatID).fold(
                {
                    _state.emit(ChatState.Error(it))
                },
                {
                    _state.emit(ChatState.Success(it))
                }
            )
        }
    }

    fun isMe(userID: String): Boolean {
        val currentState = _state.value

        return if (currentState is ChatState.Success) {
            currentState.data.me.id == userID
        } else {
            false
        }
    }

    fun getUsername(userID: String): String {
        val currentState = _state.value

        return if (currentState is ChatState.Success) {
            if (currentState.data.me.id == userID) {
                currentState.data.me.username
            } else {
                currentState.data.friend.username
            }
        } else {
            ""
        }
    }
}
