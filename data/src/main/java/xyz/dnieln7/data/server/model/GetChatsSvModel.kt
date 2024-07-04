package xyz.dnieln7.data.server.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetChatsSvModel(
    @Json(name = "data")
    val `data`: List<ChatSvModel>
)