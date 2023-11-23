package xyz.dnieln7.domain.usecase

import arrow.core.Either
import xyz.dnieln7.domain.model.Message
import xyz.dnieln7.domain.repository.MessageRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository,
    private val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase,
) {

    suspend operator fun invoke(chatID: String): Either<String, List<Message>> {
        return messageRepository.getMessages(chatID).mapLeft {
            getErrorFromThrowableUseCase(it)
        }
    }
}
