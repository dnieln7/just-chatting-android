package xyz.dnieln7.domain.usecase

import arrow.core.Either
import xyz.dnieln7.domain.repository.FriendshipRepository
import javax.inject.Inject

class SendFriendshipRequestUseCase @Inject constructor(
    private val friendshipRepository: FriendshipRepository,
    private val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase
) {

    suspend operator fun invoke(userID: String, friendID: String): Either<String, Unit> {
        return friendshipRepository.sendFriendshipRequest(userID, friendID).mapLeft {
            getErrorFromThrowableUseCase(it)
        }
    }
}
