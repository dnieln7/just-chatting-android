package xyz.dnieln7.domain.extension

import arrow.core.Either
import arrow.core.raise.Raise

inline fun <R> Raise<Throwable>.eitherCatch(block: () -> R): R {
    return Either.catch { block() }.bind()
}
