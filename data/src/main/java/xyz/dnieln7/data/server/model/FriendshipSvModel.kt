package xyz.dnieln7.data.server.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FriendshipSvModel(
    @Json(name = "id")
    val id: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "username")
    val username: String,
)