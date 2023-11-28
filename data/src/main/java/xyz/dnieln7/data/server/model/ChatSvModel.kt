package xyz.dnieln7.data.server.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatSvModel(
    @SerializedName("id")
    @Json(name = "id")
    val id: String,
    @SerializedName("me")
    @Json(name = "me")
    val me: ParticipantSvModel,
    @SerializedName("creator")
    @Json(name = "creator")
    val creator: ParticipantSvModel,
    @SerializedName("friend")
    @Json(name = "friend")
    val friend: ParticipantSvModel,
    @SerializedName("created_at")
    @Json(name = "created_at")
    val createdAt: String,
    @SerializedName("updated_at")
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