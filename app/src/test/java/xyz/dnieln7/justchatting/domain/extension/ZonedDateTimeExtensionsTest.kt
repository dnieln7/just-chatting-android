package xyz.dnieln7.justchatting.domain.extension

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import java.time.Instant

class ZonedDateTimeExtensionsTest {

    @Test
    fun `GIVEN the happy path WHEN toEpochMillis THEN return the expected result`() {
        val millis = 1696474749579
        val zonedDateTime = Instant.ofEpochMilli(millis)

        val result = zonedDateTime.toEpochMilli()

        result shouldBeEqualTo millis
    }
}