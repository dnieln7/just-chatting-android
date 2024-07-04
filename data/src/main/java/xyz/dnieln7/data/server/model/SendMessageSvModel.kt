package xyz.dnieln7.data.server.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendMessageSvModel(
    @Json(name = "chat_id")
    val chatID: String,
    @Json(name = "user_id")
    val userID: String,
    @Json(name = "message")
    val message: String,
)