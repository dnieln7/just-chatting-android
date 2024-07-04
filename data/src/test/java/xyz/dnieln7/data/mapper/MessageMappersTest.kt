package xyz.dnieln7.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import xyz.dnieln7.data.fake.buildMessageSvModel

class MessageMappersTest {

    @Test
    fun `GIVEN the happy path WHEN toDomain THEN return the expected result`() {
        val messageSvModel = buildMessageSvModel()

        val result = messageSvModel.toDomain()

        with(result) {
            id shouldBeEqualTo messageSvModel.id
            chatID shouldBeEqualTo messageSvModel.chatId
            userID shouldBeEqualTo messageSvModel.userId
            message shouldBeEqualTo messageSvModel.message
            createdAt.toString() shouldBeEqualTo messageSvModel.createdAt
            updatedAt.toString() shouldBeEqualTo messageSvModel.updatedAt
        }
    }
}