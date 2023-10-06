package xyz.dnieln7.justchatting.domain.time

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import java.time.ZoneOffset
import java.time.ZonedDateTime

class ZonedDateTimeBuildersTest {

    @Test
    fun `GIVEN the happy path WHEN buildZonedDateTimeFromEpochMillis THEN return the expected result`() {
        val millis = 1696474749579

        val result = buildZonedDateTimeFromEpochMillis(millis, ZoneOffset.UTC.normalized())
        val expectedResult = ZonedDateTime.of(2023, 10, 5, 2, 59, 9, 0, ZoneOffset.UTC.normalized())

        with(result) {
            year shouldBeEqualTo expectedResult.year
            month shouldBeEqualTo expectedResult.month
            dayOfMonth shouldBeEqualTo expectedResult.dayOfMonth
            hour shouldBeEqualTo expectedResult.hour
            minute shouldBeEqualTo expectedResult.minute
            second shouldBeEqualTo expectedResult.second
            zone shouldBeEqualTo expectedResult.zone
        }
    }
}