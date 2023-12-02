package xyz.dnieln7.data.websocket

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import xyz.dnieln7.data.BuildConfig
import xyz.dnieln7.data.mapper.toDomain
import xyz.dnieln7.data.server.model.MessageSvModel
import xyz.dnieln7.domain.model.Message
import xyz.dnieln7.domain.websocket.SingleChatConnection
import xyz.dnieln7.domain.websocket.WebSocketStatus
import java.net.URI

class DefaultSingleChatConnection(
    dispatcher: CoroutineDispatcher,
    private val gson: Gson,
) : SingleChatConnection {
    private val scope = CoroutineScope(SupervisorJob() + dispatcher)

    private val _status = MutableSharedFlow<WebSocketStatus>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    override val status = _status.asSharedFlow()

    private val _events = MutableSharedFlow<Message>()
    override val events = _events.asSharedFlow()

    private var webSocketClient: WebSocketClient? = null

    override fun connect(userID: String, chatID: String) {
        scope.launch { _status.emit(WebSocketStatus.CONNECTING) }

        val url = "${BuildConfig.JUST_CHATTING_WS}users/$userID/connect/$chatID"

        webSocketClient = ChatWebSocketClient(url).apply {
            connect()
        }
    }

    override fun sendMessage(message: String) {
        webSocketClient?.send(message)
    }

    override fun disconnect() {
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

                    _events.emit(message)
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
