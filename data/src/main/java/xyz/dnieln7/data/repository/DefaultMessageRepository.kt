package xyz.dnieln7.data.repository

import arrow.core.Either
import xyz.dnieln7.data.mapper.toDomain
import xyz.dnieln7.data.server.JustChattingApiService
import xyz.dnieln7.data.server.model.SendMessageSvModel
import xyz.dnieln7.domain.model.Message
import xyz.dnieln7.domain.repository.MessageRepository

class DefaultMessageRepository(
    private val justChattingApiService: JustChattingApiService,
) : MessageRepository {

    override suspend fun getMessages(chatID: String): Either<Throwable, List<Message>> {
        return Either.catch {
            justChattingApiService.getMessagesEager(chatID).data.map { it.toDomain() }
        }
    }

    override suspend fun sendMessage(
        chatID: String,
        userID: String,
        message: String
    ): Either<Throwable, Unit> {
        return Either.catch {
            val sendMessageSvModel = SendMessageSvModel(chatID, userID, message)

            justChattingApiService.sendMessage(sendMessageSvModel)
        }
    }
}