package xyz.dnieln7.domain.extension

import java.time.ZonedDateTime

fun ZonedDateTime.toEpochMillis(): Long {
    return toEpochSecond() * MILLISECONDS_IN_A_SECOND
}

private const val MILLISECONDS_IN_A_SECOND = 1000L
