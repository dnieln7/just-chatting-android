package xyz.dnieln7.justchatting.data.server.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginSvModel(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String,
)
