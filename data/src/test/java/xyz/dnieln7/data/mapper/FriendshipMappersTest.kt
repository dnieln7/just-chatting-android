package xyz.dnieln7.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import xyz.dnieln7.data.database.model.FriendshipDbModel
import xyz.dnieln7.data.server.model.FriendshipSvModel

class FriendshipMappersTest {

    @Test
    fun `GIVEN a FriendshipSvModel WHEN toDbModel THEN return the expected result`() {
        val friendshipSvModel = FriendshipSvModel("id", "email", "username")

        val result = friendshipSvModel.toDbModel()

        with(result) {
            id shouldBeEqualTo friendshipSvModel.id
            email shouldBeEqualTo friendshipSvModel.email
            username shouldBeEqualTo friendshipSvModel.username
        }
    }

    @Test
    fun `GIVEN a FriendshipSvModel WHEN toDomain THEN return the expected result`() {
        val friendshipSvModel = FriendshipSvModel("id", "email", "username")

        val result = friendshipSvModel.toDomain()

        with(result) {
            id shouldBeEqualTo friendshipSvModel.id
            email shouldBeEqualTo friendshipSvModel.email
            username shouldBeEqualTo friendshipSvModel.username
        }
    }

    @Test
    fun `GIVEN a FriendshipDbModel WHEN toDomain THEN return the expected result`() {
        val friendshipDbModel = FriendshipDbModel("id", "email", "username")

        val result = friendshipDbModel.toDomain()

        with(result) {
            id shouldBeEqualTo friendshipDbModel.id
            email shouldBeEqualTo friendshipDbModel.email
            username shouldBeEqualTo friendshipDbModel.username
        }
    }
}