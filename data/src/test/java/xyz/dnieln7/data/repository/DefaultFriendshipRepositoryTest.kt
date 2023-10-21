package xyz.dnieln7.data.repository

import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldContainSame
import org.amshove.kluent.shouldNotBeNull
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import xyz.dnieln7.data.database.dao.FriendshipDao
import xyz.dnieln7.data.exception.FriendshipDuplicatedException
import xyz.dnieln7.data.mapper.toDbModel
import xyz.dnieln7.data.server.JustChattingApiService
import xyz.dnieln7.data.server.model.FriendshipSvModel
import xyz.dnieln7.data.server.model.SendFriendshipRequestSvModel
import xyz.dnieln7.domain.provider.ResourceProvider
import xyz.dnieln7.testing.coVerifyOnce
import xyz.dnieln7.testing.fake.buildException
import xyz.dnieln7.testing.fake.buildFriendships
import xyz.dnieln7.testing.fake.buildUser
import xyz.dnieln7.testing.relaxedMockk

class DefaultFriendshipRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val justChattingApiService = relaxedMockk<JustChattingApiService>()
    private val friendshipDao = relaxedMockk<FriendshipDao>()
    private val resourceProvider = relaxedMockk<ResourceProvider>()

    private lateinit var repository: DefaultFriendshipRepository

    @Before
    fun setup() {
        repository = DefaultFriendshipRepository(justChattingApiService, friendshipDao, resourceProvider)
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

    @Test
    fun `GIVEN the happy path WHEN getFriendships THEN call the expected functions`() {
        val user = buildUser()
        val friendships = buildFriendships()
        val friendshipSvModels = friendships.map { FriendshipSvModel(it.id, it.email, it.username) }
        val friendshipBdModels = friendshipSvModels.map { it.toDbModel() }

        coEvery { justChattingApiService.getFriendships(user.id) } returns friendshipSvModels
        coEvery { friendshipDao.getFriendships() } returns friendshipBdModels

        runTest(dispatcher) {
            repository.getFriendships(user.id)

            coVerifyOnce {
                friendshipDao.insertFriendships(friendshipBdModels)
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN getFriendships THEN return the expected result`() {
        val user = buildUser()
        val friendships = buildFriendships()
        val friendshipSvModels = friendships.map { FriendshipSvModel(it.id, it.email, it.username) }
        val friendshipBdModels = friendshipSvModels.map { it.toDbModel() }

        coEvery { justChattingApiService.getFriendships(user.id) } returns friendshipSvModels
        coEvery { friendshipDao.getFriendships() } returns friendshipBdModels

        runTest(dispatcher) {
            val result = repository.getFriendships(user.id).getOrNull()

            result.shouldNotBeNull()
            result shouldContainSame friendships
        }
    }

    @Test
    fun `GIVEN a Throwable from justChattingApiService_getFriendships WHEN getFriendships THEN return the expected result`() {
        val user = buildUser()
        val throwable = buildException()

        coEvery { justChattingApiService.getFriendships(user.id) } throws throwable

        runTest(dispatcher) {
            val result = repository.getFriendships(user.id).swap().getOrNull()

            result shouldBeEqualTo throwable
        }
    }

    @Test
    fun `GIVEN a Throwable from friendshipDao_insertFriendships WHEN getFriendships THEN return the expected result`() {
        val user = buildUser()
        val throwable = buildException()
        val friendships = buildFriendships()
        val friendshipSvModels = friendships.map { FriendshipSvModel(it.id, it.email, it.username) }
        val friendshipBdModels = friendshipSvModels.map { it.toDbModel() }

        coEvery { justChattingApiService.getFriendships(user.id) } returns friendshipSvModels
        coEvery { friendshipDao.insertFriendships(friendshipBdModels) } throws throwable

        runTest(dispatcher) {
            val result = repository.getFriendships(user.id).swap().getOrNull()

            result shouldBeEqualTo throwable
        }
    }

    @Test
    fun `GIVEN a Throwable from friendshipDao_getFriendships WHEN getFriendships THEN return the expected result`() {
        val user = buildUser()
        val throwable = buildException()
        val friendships = buildFriendships()
        val friendshipSvModels = friendships.map { FriendshipSvModel(it.id, it.email, it.username) }

        coEvery { justChattingApiService.getFriendships(user.id) } returns friendshipSvModels
        coEvery { friendshipDao.getFriendships() } throws throwable

        runTest(dispatcher) {
            val result = repository.getFriendships(user.id).swap().getOrNull()

            result shouldBeEqualTo throwable
        }
    }
}