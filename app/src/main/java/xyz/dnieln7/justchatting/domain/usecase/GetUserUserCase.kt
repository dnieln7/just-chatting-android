package xyz.dnieln7.justchatting.domain.usecase

import arrow.core.Option
import kotlinx.coroutines.flow.first
import xyz.dnieln7.justchatting.domain.model.User
import xyz.dnieln7.justchatting.domain.provider.PreferencesProvider
import javax.inject.Inject

class GetUserUserCase @Inject constructor(private val preferencesProvider: PreferencesProvider) {

    suspend operator fun invoke(): Option<User> {
        return Option.catch { preferencesProvider.getUser().first()!! }
    }
}
