package xyz.dnieln7.data.server.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageSvModel(
    @SerializedName("id")
    @Json(name = "id")
    val id: String,
    @SerializedName("chat_id")
    @Json(name = "chat_id")
    val chatId: String,
    @SerializedName("user_id")
    @Json(name = "user_id")
    val userId: String,
    @SerializedName("message")
    @Json(name = "message")
    val message: String,
    @SerializedName("created_at")
    @Json(name = "created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    @Json(name = "updated_at")
    val updatedAt: String
)