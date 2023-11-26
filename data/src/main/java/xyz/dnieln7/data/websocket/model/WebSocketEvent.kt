package xyz.dnieln7.data.websocket.model

sealed class WebSocketEvent<out T> {
    object Idle : WebSocketEvent<Nothing>()
    class Data<T>(val data: T) : WebSocketEvent<T>()
}
