package xyz.dnieln7.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import xyz.dnieln7.data.fake.buildParticipantSvModel

class ParticipantMappersTest {

    @Test
    fun `GIVEN the happy path WHEN toDomain THEN return the expected result`() {
        val participantSvModel = buildParticipantSvModel()

        val result = participantSvModel.toDomain()

        with(result) {
            id shouldBeEqualTo id
            email shouldBeEqualTo email
            username shouldBeEqualTo username
        }
    }
}