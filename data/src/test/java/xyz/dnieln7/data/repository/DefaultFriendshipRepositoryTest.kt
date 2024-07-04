package xyz.dnieln7.data.repository

import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import xyz.dnieln7.data.exception.FriendshipDuplicatedException
import xyz.dnieln7.data.server.JustChattingApiService
import xyz.dnieln7.data.server.model.SendFriendshipRequestSvModel
import xyz.dnieln7.domain.provider.ResourceProvider
import xyz.dnieln7.testing.relaxedMockk

class DefaultFriendshipRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val justChattingApiService = relaxedMockk<JustChattingApiService>()
    private val resourceProvider = relaxedMockk<ResourceProvider>()

    private lateinit var repository: DefaultFriendshipRepository

    @Before
    fun setup() {
        repository = DefaultFriendshipRepository(justChattingApiService, resourceProvider)
    }

    @Test
    fun `GIVEN a 409 code WHEN sendFriendshipRequest THEN return the expected result`() {
        val userID = "userID"
        val friendID = "friendID"
        val error = "friendship duplicated"
        val throwable = HttpException(Response.error<Void>(409, "".toResponseBody()))

        coEvery { justChattingApiService.sendFriendshipRequest(SendFriendshipRequestSvModel(userID, friendID)) } throws throwable
        every { resourceProvider.getString(any()) } returns error

        runTest(dispatcher) {
            val result = repository.sendFriendshipRequest(userID, friendID).swap().getOrNull()

            result shouldBeInstanceOf FriendshipDuplicatedException::class
            result?.message shouldBeEqualTo error
        }
    }
}