package xyz.dnieln7.domain.usecase

import arrow.core.Either
import xyz.dnieln7.domain.model.Friendship
import xyz.dnieln7.domain.repository.FriendshipRepository
import javax.inject.Inject

class GetPendingFriendshipsUseCase @Inject constructor(
    private val friendshipRepository: FriendshipRepository,
    private val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase
) {

    suspend operator fun invoke(userID: String): Either<String, List<Friendship>> {
        return friendshipRepository.getPendingFriendships(userID).mapLeft {
            getErrorFromThrowableUseCase(it)
        }
    }
}
