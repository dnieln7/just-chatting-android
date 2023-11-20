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
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.domain.preferences.DataStorePreferences
import xyz.dnieln7.domain.repository.ChatRepository
import xyz.dnieln7.testing.fake.buildChats
import xyz.dnieln7.testing.fake.buildException
import xyz.dnieln7.testing.fake.buildUser
import xyz.dnieln7.testing.relaxedMockk

class GetChatsUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val dataStorePreferences = relaxedMockk<DataStorePreferences>()
    private val chatRepository = relaxedMockk<ChatRepository>()
    private val getErrorFromThrowableUseCase = relaxedMockk<GetErrorFromThrowableUseCase>()

    private lateinit var useCase: GetChatsUseCase

    @Before
    fun setup() {
        useCase = GetChatsUseCase(
            dataStorePreferences = dataStorePreferences,
            chatRepository = chatRepository,
            getErrorFromThrowableUseCase = getErrorFromThrowableUseCase,
        )
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return the expected result`() {
        val user = buildUser()
        val chats = buildChats()

        coEvery { dataStorePreferences.getUser() } returns flowOf(user)
        coEvery { chatRepository.getChats(user.id) } returns chats.right()

        runTest(dispatcher) {
            val result = useCase().getOrNull()

            result.shouldNotBeNull()
            result shouldContainSame chats
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
        coEvery { chatRepository.getChats(user.id) } returns throwable.left()
        every { getErrorFromThrowableUseCase(throwable) } returns throwable.localizedMessage!!

        runTest(dispatcher) {
            val result = useCase().swap().getOrNull()

            result shouldBeEqualTo throwable.localizedMessage
        }
    }
}