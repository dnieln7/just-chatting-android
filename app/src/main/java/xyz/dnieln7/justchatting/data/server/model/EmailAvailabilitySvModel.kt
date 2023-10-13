package xyz.dnieln7.justchatting.data.server.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmailAvailabilitySvModel(
    @Json(name = "email")
    val email: String,
)
