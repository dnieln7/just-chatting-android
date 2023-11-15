package xyz.dnieln7.domain.model

import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import xyz.dnieln7.testing.fake.buildUser

class UserTest {

    @Test
    fun `GIVEN the happy path WHEN fromSingleLineString THEN return the expected result`() {
        val user = buildUser()
        val singleLineString = user.toSingleLineString()

        val result = User.fromSingleLineString(singleLineString)

        result.shouldNotBeNull()

        with(result) {
            id shouldBeEqualTo user.id
            email shouldBeEqualTo user.email
            username shouldBeEqualTo user.username
            createdAt shouldBeEqualTo user.createdAt
            updatedAt shouldBeEqualTo user.updatedAt
        }
    }
}