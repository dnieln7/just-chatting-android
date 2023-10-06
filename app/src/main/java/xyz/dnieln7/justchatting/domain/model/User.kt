package xyz.dnieln7.justchatting.domain.model

import xyz.dnieln7.justchatting.domain.extension.toEpochMillis
import xyz.dnieln7.justchatting.domain.time.buildZonedDateTimeFromEpochMillis
import java.time.ZonedDateTime

data class User(
    val id: String,
    val email: String,
    val username: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
) {

    fun toSingleLineString(): String {
        val stringBuilder = StringBuilder()

        stringBuilder.append(id)
        stringBuilder.append(SEPARATOR)
        stringBuilder.append(email)
        stringBuilder.append(SEPARATOR)
        stringBuilder.append(username)
        stringBuilder.append(SEPARATOR)
        stringBuilder.append(createdAt.toEpochMillis())
        stringBuilder.append(SEPARATOR)
        stringBuilder.append(updatedAt.toEpochMillis())

        return stringBuilder.toString()
    }

    companion object {
        fun fromSingleLineString(string: String): User? {
            return try {
                val values = string.split(SEPARATOR)

                User(
                    values[0],
                    values[1],
                    values[2],
                    buildZonedDateTimeFromEpochMillis(values[3].toLong()),
                    buildZonedDateTimeFromEpochMillis(values[4].toLong()),
                )
            } catch (ignored: Exception) {
                null
            }
        }
    }
}

private const val SEPARATOR = "|"
