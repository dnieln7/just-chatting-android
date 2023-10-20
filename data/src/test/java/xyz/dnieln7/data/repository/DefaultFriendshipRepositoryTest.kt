package xyz.dnieln7.data.repository

import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContainAll
import org.amshove.kluent.shouldNotBeNull
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.data.database.dao.FriendshipDao
import xyz.dnieln7.data.mapper.toDbModel
import xyz.dnieln7.data.server.JustChattingApiService
import xyz.dnieln7.data.server.model.FriendshipSvModel
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

    private lateinit var repository: DefaultFriendshipRepository

    @Before
    fun setup() {
        repository = DefaultFriendshipRepository(justChattingApiService, friendshipDao)
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
            result shouldContainAll friendships
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