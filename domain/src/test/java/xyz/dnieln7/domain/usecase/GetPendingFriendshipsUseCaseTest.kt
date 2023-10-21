package xyz.dnieln7.domain.usecase

import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContainSame
import org.amshove.kluent.shouldNotBeNull
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.domain.repository.FriendshipRepository
import xyz.dnieln7.testing.fake.buildException
import xyz.dnieln7.testing.fake.buildFriendships
import xyz.dnieln7.testing.relaxedMockk

class GetPendingFriendshipsUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val friendshipRepository = relaxedMockk<FriendshipRepository>()
    private val getErrorFromThrowableUseCase = relaxedMockk<GetErrorFromThrowableUseCase>()

    private lateinit var useCase: GetPendingFriendshipsUseCase

    @Before
    fun setup() {
        useCase = GetPendingFriendshipsUseCase(friendshipRepository, getErrorFromThrowableUseCase)
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return the expected result`() {
        val userID = "userID"
        val friendships = buildFriendships()

        coEvery { friendshipRepository.getPendingFriendships(userID) } returns friendships.right()

        runTest(dispatcher) {
            val result = useCase(userID).getOrNull()

            result.shouldNotBeNull()
            result shouldContainSame friendships
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN invoke THEN return the expected result`() {
        val userID = "userID"
        val throwable = buildException()

        coEvery { friendshipRepository.getPendingFriendships(userID) } returns throwable.left()
        every { getErrorFromThrowableUseCase(throwable) } returns throwable.localizedMessage!!

        runTest(dispatcher) {
            val result = useCase(userID).swap().getOrNull()

            result shouldBeEqualTo throwable.localizedMessage
        }
    }
}