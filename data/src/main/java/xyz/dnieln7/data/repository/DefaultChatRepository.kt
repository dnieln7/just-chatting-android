package xyz.dnieln7.data.repository

import arrow.core.Either
import xyz.dnieln7.data.mapper.toDomain
import xyz.dnieln7.data.server.JustChattingApiService
import xyz.dnieln7.domain.model.Chat
import xyz.dnieln7.domain.repository.ChatRepository

class DefaultChatRepository(
    private val justChattingApiService: JustChattingApiService,
) : ChatRepository {

    override suspend fun getChats(userID: String): Either<Throwable, List<Chat>> {
        return Either.catch {
            val getChatsSvModel = justChattingApiService.getChats(userID)

            getChatsSvModel.data.map { it.toDomain() }
        }
    }

    override suspend fun getChat(userID: String, chatID: String): Either<Throwable, Chat> {
        return Either.catch {
            val chatSvModel = justChattingApiService.getChat(userID, chatID)

            chatSvModel.toDomain()
        }
    }
}