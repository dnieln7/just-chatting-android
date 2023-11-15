package xyz.dnieln7.domain.usecase

import xyz.dnieln7.domain.R
import xyz.dnieln7.domain.provider.ResourceProvider
import javax.inject.Inject

class GetErrorFromThrowableUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
) {

    operator fun invoke(throwable: Throwable): String {
        return throwable.localizedMessage ?: resourceProvider.getString(R.string.unknown_error)
    }
}