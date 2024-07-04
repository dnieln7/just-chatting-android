package xyz.dnieln7.domain.model

import java.time.ZonedDateTime

data class Message(
    val id: String,
    val chatID: String,
    val userID: String,
    val message: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
)
