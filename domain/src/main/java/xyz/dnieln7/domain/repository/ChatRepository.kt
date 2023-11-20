package xyz.dnieln7.domain.repository

import arrow.core.Either
import xyz.dnieln7.domain.model.Chat

interface ChatRepository {
    suspend fun getChats(userID: String): Either<Throwable, List<Chat>>
}