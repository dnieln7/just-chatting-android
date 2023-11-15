package xyz.dnieln7.domain.usecase

import arrow.core.Either
import arrow.core.raise.either
import kotlinx.coroutines.flow.first
import xyz.dnieln7.domain.extension.eitherCatch
import xyz.dnieln7.domain.model.Friendship
import xyz.dnieln7.domain.preferences.DataStorePreferences
import xyz.dnieln7.domain.repository.FriendshipRepository
import javax.inject.Inject

class GetFriendshipsUseCase @Inject constructor(
    private val dataStorePreferences: DataStorePreferences,
    private val friendshipRepository: FriendshipRepository,
    private val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase,
) {

    suspend operator fun invoke(): Either<String, List<Friendship>> {
        return either {
            val user = eitherCatch { dataStorePreferences.getUser().first()!! }

            friendshipRepository.getFriendships(user.id).bind()
        }.mapLeft {
            getErrorFromThrowableUseCase(it)
        }
    }
}
