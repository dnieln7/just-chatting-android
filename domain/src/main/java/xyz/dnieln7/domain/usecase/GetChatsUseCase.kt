package xyz.dnieln7.domain.usecase

import arrow.core.Either
import arrow.core.raise.either
import kotlinx.coroutines.flow.first
import xyz.dnieln7.domain.extension.eitherCatch
import xyz.dnieln7.domain.model.Chat
import xyz.dnieln7.domain.preferences.DataStorePreferences
import xyz.dnieln7.domain.repository.ChatRepository
import javax.inject.Inject

class GetChatsUseCase @Inject constructor(
    private val dataStorePreferences: DataStorePreferences,
    private val chatRepository: ChatRepository,
    private val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase,
) {

    suspend operator fun invoke(): Either<String, List<Chat>> {
        return either {
            val user = eitherCatch { dataStorePreferences.getUser().first()!! }

            chatRepository.getChats(user.id).bind()
        }.mapLeft {
            getErrorFromThrowableUseCase(it)
        }
    }
}