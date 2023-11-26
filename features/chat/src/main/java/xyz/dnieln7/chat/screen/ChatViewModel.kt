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
import xyz.dnieln7.data.websocket.ChatWebSocket
import xyz.dnieln7.data.websocket.model.WebSocketEvent
import xyz.dnieln7.domain.usecase.GetChatUseCase
import xyz.dnieln7.domain.usecase.GetMessagesUseCase
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle,
    private val getChatUseCase: GetChatUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val chatWebSocket: ChatWebSocket,
) : ViewModel() {

    private val userID: String = savedStateHandle[CHAT_USER_ID]!!
    private val chatID: String = savedStateHandle[CHAT_CHAT_ID]!!

    private val _state = MutableStateFlow(ChatState())
    val state get() = _state.asStateFlow()

    init {
        getChat()
        getMessages()

        viewModelScope.launch(dispatcher) {
            chatWebSocket.status.collectLatest { status ->
                _state.update { it.copy(status = status) }
            }
        }

        viewModelScope.launch(dispatcher) {
            chatWebSocket.events.collect { event ->
                if (event is WebSocketEvent.Data) {
                    val newMessages = listOf(event.data).plus(_state.value.messages)

                    _state.update { it.copy(messages = newMessages) }
                }
            }
        }

        chatWebSocket.connect(userID, chatID)
    }

    fun getChat() {
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(loadingChat = true, chat = null, chatError = null) }

            getChatUseCase(userID, chatID).fold(
                { error ->
                    _state.update { it.copy(loadingChat = false, chatError = error) }
                },
                { chat ->
                    _state.update { it.copy(loadingChat = false, chat = chat) }
                }
            )
        }
    }

    fun getMessages() {
        viewModelScope.launch(dispatcher) {
            _state.update { it.copy(loadingMessages = true, messagesError = null) }

            getMessagesUseCase(chatID).fold(
                { error ->
                    _state.update { it.copy(loadingMessages = false, messagesError = error) }
                },
                { messages ->
                    _state.update { it.copy(loadingMessages = false, messages = messages) }
                }
            )
        }
    }

    fun sendMessage(message: String) {
        chatWebSocket.sendMessage(message)
    }

    fun isMe(userID: String): Boolean {
        return state.value.chat?.me?.id == userID
    }

    fun getUsername(userID: String): String {
        return if (isMe(userID)) {
            state.value.chat?.me?.username ?: ""
        } else {
            state.value.chat?.friend?.username ?: ""
        }
    }

    override fun onCleared() {
        chatWebSocket.disconnect()
    }
}
