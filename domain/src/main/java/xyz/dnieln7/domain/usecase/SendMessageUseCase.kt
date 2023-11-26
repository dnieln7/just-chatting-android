package xyz.dnieln7.domain.usecase

import arrow.core.Either
import xyz.dnieln7.domain.repository.MessageRepository
import javax.inject.Inject

// TODO: 24/11/23 delete
class SendMessageUseCase @Inject constructor(
    private val messageRepository: MessageRepository,
    private val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase,
) {

    suspend operator fun invoke(
        chatID: String,
        userID: String,
        message: String,
    ): Either<String, Unit> {
        return messageRepository.sendMessage(chatID, userID, message).mapLeft {
            getErrorFromThrowableUseCase(it)
        }
    }
}
