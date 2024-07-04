package xyz.dnieln7.domain.extension

import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.Test
import java.time.Instant
import java.time.ZonedDateTime

class ZonedDateTimeExtensionsTest {

    @Test
    fun `GIVEN the happy path WHEN toEpochMillis THEN return the expected result`() {
        val millis = 1696474749579
        val zonedDateTime = Instant.ofEpochMilli(millis)

        val result = zonedDateTime.toEpochMilli()

        result shouldBeEqualTo millis
    }

    @Test
    fun `GIVEN today WHEN isToday THEN return true`() {
        val zonedDateTime = ZonedDateTime.now()

        val result = zonedDateTime.isToday()

        result.shouldBeTrue()
    }

    @Test
    fun `GIVEN yesterday WHEN isToday THEN return false`() {
        val zonedDateTime = ZonedDateTime.now().minusDays(1)

        val result = zonedDateTime.isToday()

        result.shouldBeFalse()
    }
}
