package xyz.dnieln7.domain.usecase

import arrow.core.Either
import xyz.dnieln7.domain.R
import xyz.dnieln7.domain.preferences.DataStorePreferences
import xyz.dnieln7.domain.provider.ResourceProvider
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val dataStorePreferences: DataStorePreferences,
    private val resourceProvider: ResourceProvider,
) {

    suspend operator fun invoke(): Either<String, Unit> {
        return Either.catch { dataStorePreferences.clear() }.mapLeft {
            resourceProvider.getString(R.string.generic_error)
        }
    }
}
