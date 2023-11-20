package xyz.dnieln7.data.server.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetChatsSvModel(
    @Json(name = "data")
    val `data`: List<ChatSvModel>
) {
    @JsonClass(generateAdapter = true)
    data class ChatSvModel(
        @Json(name = "id")
        val id: String,
        @Json(name = "me")
        val me: ParticipantSvModel,
        @Json(name = "creator")
        val creator: ParticipantSvModel,
        @Json(name = "friend")
        val friend: ParticipantSvModel,
        @Json(name = "created_at")
        val createdAt: String,
        @Json(name = "updated_at")
        val updatedAt: String
    ) {
        @JsonClass(generateAdapter = true)
        data class ParticipantSvModel(
            @Json(name = "id")
            val id: String,
            @Json(name = "email")
            val email: String,
            @Json(name = "username")
            val username: String
        )
    }
}