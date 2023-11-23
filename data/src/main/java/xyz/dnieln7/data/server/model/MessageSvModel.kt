package xyz.dnieln7.data.server.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageSvModel(
    @Json(name = "id")
    val id: String,
    @Json(name = "chat_id")
    val chatId: String,
    @Json(name = "user_id")
    val userId: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "updated_at")
    val updatedAt: String
)