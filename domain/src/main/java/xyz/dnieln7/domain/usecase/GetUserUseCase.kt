package xyz.dnieln7.domain.usecase

import arrow.core.Option
import kotlinx.coroutines.flow.first
import xyz.dnieln7.domain.model.User
import xyz.dnieln7.domain.preferences.DataStorePreferences
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val dataStorePreferences: DataStorePreferences) {

    suspend operator fun invoke(): Option<User> {
        return Option.catch { dataStorePreferences.getUser().first()!! }
    }
}
