package xyz.dnieln7.chat.fake

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import xyz.dnieln7.domain.model.Message
import xyz.dnieln7.domain.websocket.SingleChatConnection
import xyz.dnieln7.domain.websocket.WebSocketStatus
import xyz.dnieln7.testing.fake.buildZonedDateTime
import java.util.Random

class FakeSingleChatConnection(dispatcher: CoroutineDispatcher) : SingleChatConnection {
    private val scope = CoroutineScope(SupervisorJob() + dispatcher)

    private val _status = MutableSharedFlow<WebSocketStatus>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val status = _status.asSharedFlow()

    private val _events = MutableSharedFlow<Message>()
    override val events = _events.asSharedFlow()

    private val random = Random()
    private var happyPath = true

    private var userID = ""
    private var chatID = ""

    fun setHappyPath(happyPath: Boolean) {
        this.happyPath = happyPath
    }

    override fun connect(userID: String, chatID: String) {
        this.userID = userID
        this.chatID = chatID

        scope.launch {
            _status.emit(WebSocketStatus.CONNECTING)

            delay(3000)

            if (happyPath) {
                _status.emit(WebSocketStatus.CONNECTED)
            } else {
                _status.emit(WebSocketStatus.ERROR)
            }
        }
    }

    override fun sendMessage(message: String) {
        scope.launch {
            val messageEvent = Message(
                random.nextInt(10000).plus(1).toString(),
                chatID,
                userID,
                message,
                createdAt = buildZonedDateTime(),
                updatedAt = buildZonedDateTime()
            )

            _events.emit(messageEvent)
        }
    }

    override fun disconnect() {
        scope.launch {
            delay(3000)

            _status.emit(WebSocketStatus.DISCONNECTED)
        }
    }
}