package xyz.dnieln7.justchatting.domain.time

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

fun buildZonedDateTimeFromEpochMillis(
    millis: Long,
    zoneId: ZoneId = ZoneId.systemDefault()
): ZonedDateTime {
    return Instant.ofEpochMilli(millis).atZone(zoneId)
}

fun buildZonedDateTimeFromString(string: String): ZonedDateTime {
    return ZonedDateTime.parse(string)
}
