package xyz.dnieln7.domain.repository

import arrow.core.Either
import xyz.dnieln7.domain.model.Message

interface MessageRepository {
    suspend fun getMessages(chatID: String): Either<Throwable, List<Message>>
}