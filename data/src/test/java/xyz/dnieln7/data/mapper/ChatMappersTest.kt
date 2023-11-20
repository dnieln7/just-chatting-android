package xyz.dnieln7.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import xyz.dnieln7.data.fake.buildChatSvModel

class ChatMappersTest {

    @Test
    fun `GIVEN the happy path WHEN toDomain THEN return the expected result`() {
        val chatSvModel = buildChatSvModel()

        val result = chatSvModel.toDomain()

        with(result) {
            id shouldBeEqualTo chatSvModel.id
            me.id shouldBeEqualTo chatSvModel.me.id
            me.email shouldBeEqualTo chatSvModel.me.email
            me.username shouldBeEqualTo chatSvModel.me.username
            creator.id shouldBeEqualTo chatSvModel.creator.id
            creator.email shouldBeEqualTo chatSvModel.creator.email
            creator.username shouldBeEqualTo chatSvModel.creator.username
            friend.id shouldBeEqualTo chatSvModel.friend.id
            friend.email shouldBeEqualTo chatSvModel.friend.email
            friend.username shouldBeEqualTo chatSvModel.friend.username
            createdAt.toString() shouldBeEqualTo chatSvModel.createdAt
            updatedAt.toString() shouldBeEqualTo chatSvModel.updatedAt
        }
    }
}