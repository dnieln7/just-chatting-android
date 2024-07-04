package xyz.dnieln7.data.repository

import com.google.gson.Gson
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import xyz.dnieln7.data.fake.buildChatSvModel
import xyz.dnieln7.data.mapper.toDomain
import xyz.dnieln7.data.server.JustChattingApiService
import xyz.dnieln7.data.server.model.ChatSvModel
import xyz.dnieln7.data.server.model.CreateChatSvModel
import xyz.dnieln7.testing.fake.buildException
import xyz.dnieln7.testing.relaxedMockk

class DefaultChatRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val gson = Gson()
    private val justChattingApiService = relaxedMockk<JustChattingApiService>()

    private lateinit var repository: DefaultChatRepository

    @Before
    fun setup() {
        repository = DefaultChatRepository(gson, justChattingApiService)
    }

    @Test
    fun `GIVEN the happy path WHEN createChat THEN return the expected result`() {
        val userID = "userID"
        val friendID = "friendID"
        val chatSvModel = buildChatSvModel()
        val chat = chatSvModel.toDomain()

        coEvery { justChattingApiService.createChat(CreateChatSvModel(userID, friendID)) } returns chatSvModel

        runTest(dispatcher) {
            val result = repository.createChat(userID, friendID).getOrNull()

            result shouldBeEqualTo chat
        }
    }

    @Test
    fun `GIVEN a 409 code WHEN createChat THEN return the expected result`() {
        val userID = "userID"
        val friendID = "friendID"
        val json =
            "{\"id\":\"494db2fa-80f3-41b3-9eb7-c93c428aab1d\",\"me\":{\"id\":\"41359a4e-d066-4f64-a1d8-410c2c690935\",\"email\":\"daniel.nolasco@rookmotion.com\",\"username\":\"daniel-rookmotion\"},\"creator\":{\"id\":\"0fac06d5-bb77-4def-90f7-8c0c1fcffda7\",\"email\":\"daniel.nolasco@tryrook.io\",\"username\":\"daniel-rook\"},\"friend\":{\"id\":\"0fac06d5-bb77-4def-90f7-8c0c1fcffda7\",\"email\":\"daniel.nolasco@tryrook.io\",\"username\":\"daniel-rook\"},\"created_at\":\"2023-11-19T15:00:30.348221Z\",\"updated_at\":\"2023-11-19T15:00:30.348221Z\"}"

        val chat = ChatSvModel(
            id = "494db2fa-80f3-41b3-9eb7-c93c428aab1d",
            me = ChatSvModel.ParticipantSvModel(
                id = "41359a4e-d066-4f64-a1d8-410c2c690935",
                email = "daniel.nolasco@rookmotion.com",
                username = "daniel-rookmotion",
            ),
            creator = ChatSvModel.ParticipantSvModel(
                id = "0fac06d5-bb77-4def-90f7-8c0c1fcffda7",
                email = "daniel.nolasco@tryrook.io",
                username = "daniel-rook"
            ),
            friend = ChatSvModel.ParticipantSvModel(
                id = "0fac06d5-bb77-4def-90f7-8c0c1fcffda7",
                email = "daniel.nolasco@tryrook.io",
                username = "daniel-rook"
            ),
            createdAt = "2023-11-19T15:00:30.348221Z",
            updatedAt = "2023-11-19T15:00:30.348221Z",
        ).toDomain()

        val throwable = HttpException(Response.error<ChatSvModel>(409, json.toResponseBody()))

        coEvery { justChattingApiService.createChat(CreateChatSvModel(userID, friendID)) } throws throwable

        runTest(dispatcher) {
            val result = repository.createChat(userID, friendID).getOrNull()

            result shouldBeEqualTo chat
        }
    }

    @Test
    fun `GIVEN a Throwable WHEN createChat THEN return the expected result`() {
        val userID = "userID"
        val friendID = "friendID"
        val throwable = buildException()

        coEvery { justChattingApiService.createChat(CreateChatSvModel(userID, friendID)) } throws throwable

        runTest(dispatcher) {
            val result = repository.createChat(userID, friendID).swap().getOrNull()

            result shouldBeEqualTo throwable
        }
    }
}

