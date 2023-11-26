package xyz.dnieln7.data.websocket

import com.google.gson.Gson
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import xyz.dnieln7.coroutines.di.IO
import xyz.dnieln7.data.BuildConfig
import xyz.dnieln7.data.mapper.toDomain
import xyz.dnieln7.data.server.model.MessageSvModel
import xyz.dnieln7.data.websocket.model.WebSocketEvent
import xyz.dnieln7.data.websocket.model.WebSocketStatus
import xyz.dnieln7.domain.model.Message
import java.net.URI
import javax.inject.Inject

@ViewModelScoped
class ChatWebSocket @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher,
    private val gson: Gson,
) {
    private val scope = CoroutineScope(SupervisorJob() + dispatcher)

    private val _status = MutableSharedFlow<WebSocketStatus>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val status = _status.asSharedFlow()

    private val _events = MutableSharedFlow<WebSocketEvent<Message>>()
    val events = _events.asSharedFlow()

    private var webSocketClient: WebSocketClient? = null

    init {
        scope.launch { _events.emit(WebSocketEvent.Idle) }
    }

    fun connect(userID: String, chatID: String) {
        scope.launch { _status.emit(WebSocketStatus.CONNECTING) }

        val url = "${BuildConfig.JUST_CHATTING_WS}users/$userID/connect/$chatID"

        webSocketClient = ChatWebSocketClient(url).apply {
            connect()
        }
    }

    fun sendMessage(message: String) {
        webSocketClient?.send(message)
    }

    fun disconnect() {
        webSocketClient?.close()
    }

    inner class ChatWebSocketClient(url: String) : WebSocketClient(URI(url)) {
        override fun onOpen(handshakedata: ServerHandshake?) {
            scope.launch { _status.emit(WebSocketStatus.CONNECTED) }
        }

        override fun onMessage(json: String?) {
            scope.launch {
                if (json != null) {
                    val message = gson.fromJson(json, MessageSvModel::class.java).toDomain()

                    _events.emit(WebSocketEvent.Data(message))
                } else {
                    _events.emit(WebSocketEvent.Idle)
                }
            }
        }

        override fun onClose(code: Int, reason: String?, remote: Boolean) {
            scope.launch { _status.emit(WebSocketStatus.DISCONNECTED) }
        }

        override fun onError(ex: Exception?) {
            scope.launch { _status.emit(WebSocketStatus.ERROR) }
        }
    }
}
