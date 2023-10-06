package xyz.dnieln7.justchatting.domain.usecase

import xyz.dnieln7.justchatting.R
import xyz.dnieln7.justchatting.domain.provider.ResourceProvider
import javax.inject.Inject

class GetErrorFromThrowableUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
) {

    operator fun invoke(throwable: Throwable): String {
        return throwable.localizedMessage ?: resourceProvider.getString(R.string.unknown_error)
    }
}