package xyz.dnieln7.domain.usecase

import arrow.core.Either
import xyz.dnieln7.domain.model.Chat
import xyz.dnieln7.domain.repository.ChatRepository
import javax.inject.Inject

class GetChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository,
    private val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase,
) {

    suspend operator fun invoke(userID: String, chatID: String): Either<String, Chat> {
        return chatRepository.getChat(userID, chatID).mapLeft {
            getErrorFromThrowableUseCase(it)
        }
    }
}