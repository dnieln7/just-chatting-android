package xyz.dnieln7.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import xyz.dnieln7.data.server.model.UserSvModel

class UserMappersTest {

    @Test
    fun `GIVEN a UserSvModel WHEN toDomain THEN return the expected result`() {
        val userSvModel = UserSvModel("id", "email", "username", "2023-09-09T20:05:48.363565Z", "2023-09-09T20:05:48.363565Z")

        val result = userSvModel.toDomain()

        with(result) {
            id shouldBeEqualTo userSvModel.id
            email shouldBeEqualTo userSvModel.email
            username shouldBeEqualTo userSvModel.username
            createdAt.toString() shouldBeEqualTo userSvModel.createdAt
            updatedAt.toString() shouldBeEqualTo userSvModel.updatedAt
        }
    }
}