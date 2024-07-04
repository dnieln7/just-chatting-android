package xyz.dnieln7.domain.extension

import java.time.LocalDate
import java.time.ZonedDateTime

fun ZonedDateTime.isToday(): Boolean {
    return toLocalDate() == LocalDate.now(this.zone)
}

fun ZonedDateTime.toEpochMillis(): Long {
    return toEpochSecond() * MILLISECONDS_IN_A_SECOND
}

private const val MILLISECONDS_IN_A_SECOND = 1000L
