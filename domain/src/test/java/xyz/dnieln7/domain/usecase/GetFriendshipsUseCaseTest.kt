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
import org.amshove.kluent.shouldContainSame
import org.amshove.kluent.shouldNotBeNull
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.domain.preferences.DataStorePreferences
import xyz.dnieln7.domain.repository.FriendshipRepository
import xyz.dnieln7.testing.fake.buildException
import xyz.dnieln7.testing.fake.buildFriendships
import xyz.dnieln7.testing.fake.buildUser
import xyz.dnieln7.testing.relaxedMockk

class GetFriendshipsUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val friendshipRepository = relaxedMockk<FriendshipRepository>()
    private val dataStorePreferences = relaxedMockk<DataStorePreferences>()
    private val getErrorFromThrowableUseCase = relaxedMockk<GetErrorFromThrowableUseCase>()

    private lateinit var useCase: GetFriendshipsUseCase

    @Before
    fun setup() {
        useCase = GetFriendshipsUseCase(
            friendshipRepository = friendshipRepository,
            dataStorePreferences = dataStorePreferences,
            getErrorFromThrowableUseCase = getErrorFromThrowableUseCase,
        )
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return the expected result`() {
        val user = buildUser()
        val friendships = buildFriendships()

        coEvery { dataStorePreferences.getUser() } returns flowOf(user)
        coEvery { friendshipRepository.getFriendships(user.id) } returns friendships.right()

        runTest(dispatcher) {
            val result = useCase().getOrNull()

            result.shouldNotBeNull()
            result shouldContainSame friendships
        }
    }

    @Test
    fun `GIVEN null WHEN invoke THEN return the expected result`() {
        val error = "Error"

        coEvery { dataStorePreferences.getUser() } returns flowOf(null)
        every { getErrorFromThrowableUseCase(any()) } returns error

        runTest(dispatcher) {
            val result = useCase().swap().getOrNull()

            result shouldBeEqualTo error
        }
    }

    @Test
    fun `GIVEN emptyFlow WHEN invoke THEN return the expected result`() {
        val error = "Error"

        coEvery { dataStorePreferences.getUser() } returns emptyFlow()
        every { getErrorFromThrowableUseCase(any()) } returns error

        runTest(dispatcher) {
            val result = useCase().swap().getOrNull()

            result shouldBeEqualTo error
        }
    }

    @Test
    fun `GIVEN a Throwable WHEN invoke THEN return the expected result`() {
        val user = buildUser()
        val throwable = buildException()

        coEvery { dataStorePreferences.getUser() } returns flowOf(user)
        coEvery { friendshipRepository.getFriendships(user.id) } returns throwable.left()
        every { getErrorFromThrowableUseCase(throwable) } returns throwable.localizedMessage!!

        runTest(dispatcher) {
            val result = useCase().swap().getOrNull()

            result shouldBeEqualTo throwable.localizedMessage
        }
    }
}