package xyz.dnieln7.domain.usecase

import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.domain.preferences.DataStorePreferences
import xyz.dnieln7.domain.repository.FriendshipRepository
import xyz.dnieln7.testing.fake.buildException
import xyz.dnieln7.testing.fake.buildUser
import xyz.dnieln7.testing.relaxedMockk

class SendFriendshipRequestUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val dataStorePreferences = relaxedMockk<DataStorePreferences>()
    private val friendshipRepository = relaxedMockk<FriendshipRepository>()
    private val getErrorFromThrowableUseCase = relaxedMockk<GetErrorFromThrowableUseCase>()

    private lateinit var useCase: SendFriendshipRequestUseCase

    @Before
    fun setup() {
        useCase = SendFriendshipRequestUseCase(dataStorePreferences, friendshipRepository, getErrorFromThrowableUseCase)
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return the expected result`() {
        val friendID = "friendID"
        val user = buildUser()

        coEvery { dataStorePreferences.getUser() } returns flowOf(user)
        coEvery { friendshipRepository.sendFriendshipRequest(user.id, friendID) } returns Unit.right()

        runTest(dispatcher) {
            val result = useCase(friendID).getOrNull()

            result shouldBeEqualTo Unit
        }
    }

    @Test
    fun `GIVEN null WHEN invoke THEN return the expected result`() {
        val error = "Error"
        val friendID = "friendID"

        coEvery { dataStorePreferences.getUser() } returns flowOf(null)
        every { getErrorFromThrowableUseCase(any()) } returns error

        runTest(dispatcher) {
            val result = useCase(friendID).swap().getOrNull()

            result shouldBeEqualTo error
        }
    }

    @Test
    fun `GIVEN emptyFlow WHEN invoke THEN return the expected result`() {
        val error = "Error"
        val friendID = "friendID"

        coEvery { dataStorePreferences.getUser() } returns emptyFlow()
        every { getErrorFromThrowableUseCase(any()) } returns error

        runTest(dispatcher) {
            val result = useCase(friendID).swap().getOrNull()

            result shouldBeEqualTo error
        }
    }

    @Test
    fun `GIVEN a Throwable WHEN invoke THEN return the expected result`() {
        val friendID = "friendID"
        val user = buildUser()
        val throwable = buildException()

        coEvery { dataStorePreferences.getUser() } returns flowOf(user)
        coEvery { friendshipRepository.sendFriendshipRequest(user.id, friendID) } returns throwable.left()
        every { getErrorFromThrowableUseCase(throwable) } returns throwable.localizedMessage!!

        runTest(dispatcher) {
            val result = useCase(friendID).swap().getOrNull()

            result shouldBeEqualTo throwable.localizedMessage
        }
    }
}