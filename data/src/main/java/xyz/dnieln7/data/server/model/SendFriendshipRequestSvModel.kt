package xyz.dnieln7.data.server.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendFriendshipRequestSvModel(
    @Json(name = "user_id")
    val userID: String,
    @Json(name = "friend_id")
    val friendID: String,
)
