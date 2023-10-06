package xyz.dnieln7.justchatting.fake

import java.time.ZoneId
import java.time.ZonedDateTime

fun buildZonedDateTime(): ZonedDateTime {
    return ZonedDateTime.now(ZoneId.of("UTC"))
}
