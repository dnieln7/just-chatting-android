package xyz.dnieln7.domain.model

import java.time.ZonedDateTime

data class Chat(
    val id: String,
    val me: Participant,
    val creator: Participant,
    val friend: Participant,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
)
