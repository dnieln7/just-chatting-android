package xyz.dnieln7.chat.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.dnieln7.chat.navigation.CHAT_CHAT_ID
import xyz.dnieln7.chat.navigation.CHAT_USER_ID
import xyz.dnieln7.coroutines.di.IO
import xyz.dnieln7.domain.usecase.GetMessagesUseCase
import xyz.dnieln7.domain.websocket.SingleChatConnection
import xyz.dnieln7.domain.websocket.WebSocketStatus
import javax.inject.Inject

@HiltViewModel
class ChatConnectionViewModel @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val singleChatConnection: SingleChatConnection,
) : ViewModel() {

    private val userID: String = savedStateHandle[CHAT_USER_ID]!!
    private val chatID: String = savedStateHandle[CHAT_CHAT_ID]!!

    private val _state = MutableStateFlow(ChatConnectionState())
    val state get() = _state.asStateFlow()

    init {
        viewModelScope.launch(dispatcher) {
            singleChatConnection.status.collectLatest { webSocketStatus ->
                when (webSocketStatus) {
                    WebSocketStatus.DISCONNECTED -> {
                        // Ignored
                    }

                    WebSocketStatus.CONNECTING -> _state.update {
                        it.copy(status = ChatConnectionStatus.CONNECTING)
                    }

                    WebSocketStatus.ERROR -> _state.update {
                        it.copy(status = ChatConnectionStatus.CONNECTION_ERROR)
                    }

                    WebSocketStatus.CONNECTED -> {
                        _state.update { it.copy(status = ChatConnectionStatus.CONNECTED) }
                    }
                }
            }
        }

        viewModelScope.launch(dispatcher) {
            singleChatConnection.events.collect { event ->
                val newMessages = listOf(event).plus(_state.value.messages)

                _state.update { it.copy(messages = newMessages) }
            }
        }

        getMessages()
    }

    fun connect() {
        singleChatConnection.connect(userID, chatID)
    }

    fun getMessages() {
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(status = ChatConnectionStatus.FETCHING_MESSAGES) }

            getMessagesUseCase(chatID).fold(
                { _ ->
                    _state.update { it.copy(status = ChatConnectionStatus.MESSAGES_ERROR) }
                },
                { messages ->
                    _state.update { it.copy(messages = messages) }

                    connect()
                }
            )
        }
    }

    fun sendMessage(message: String) {
        singleChatConnection.sendMessage(message)
    }

    override fun onCleared() {
        singleChatConnection.disconnect()
    }
}
