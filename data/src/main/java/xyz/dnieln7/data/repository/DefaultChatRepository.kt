package xyz.dnieln7.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.google.gson.Gson
import retrofit2.HttpException
import xyz.dnieln7.data.mapper.toDomain
import xyz.dnieln7.data.server.JustChattingApiService
import xyz.dnieln7.data.server.extension.getErrorBodyAsString
import xyz.dnieln7.data.server.extension.hasErrorBody
import xyz.dnieln7.data.server.extension.isConflict
import xyz.dnieln7.data.server.model.ChatSvModel
import xyz.dnieln7.data.server.model.CreateChatSvModel
import xyz.dnieln7.domain.model.Chat
import xyz.dnieln7.domain.repository.ChatRepository

class DefaultChatRepository(
    private val gson: Gson,
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

    override suspend fun createChat(userID: String, friendID: String): Either<Throwable, Chat> {
        return Either.catch {
            val createChatSvModel = CreateChatSvModel(userID, friendID)

            justChattingApiService.createChat(createChatSvModel).toDomain()
        }.fold(
            {
                if (it is HttpException && it.isConflict() && it.hasErrorBody()) {
                    val json = it.getErrorBodyAsString()

                    return gson.fromJson(json, ChatSvModel::class.java).toDomain().right()
                } else {
                    it.left()
                }
            },
            {
                it.right()
            }
        )
    }
}