package xyz.dnieln7.domain.usecase

import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.domain.repository.ChatRepository
import xyz.dnieln7.testing.fake.buildChat
import xyz.dnieln7.testing.fake.buildException
import xyz.dnieln7.testing.relaxedMockk

class GetChatUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val chatRepository = relaxedMockk<ChatRepository>()
    private val getErrorFromThrowableUseCase = relaxedMockk<GetErrorFromThrowableUseCase>()

    private lateinit var useCase: GetChatUseCase

    @Before
    fun setup() {
        useCase = GetChatUseCase(
            chatRepository = chatRepository,
            getErrorFromThrowableUseCase = getErrorFromThrowableUseCase,
        )
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return the expected result`() {
        val userID = "user_id"
        val chatID = "chat_id"
        val chat = buildChat()

        coEvery { chatRepository.getChat(userID, chatID) } returns chat.right()

        runTest(dispatcher) {
            val result = useCase(userID, chatID).getOrNull()

            result shouldBeEqualTo chat
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN invoke THEN return the expected result`() {
        val userID = "user_id"
        val chatID = "chat_id"
        val throwable = buildException()

        coEvery { chatRepository.getChat(userID, chatID) } returns throwable.left()
        every { getErrorFromThrowableUseCase(throwable) } returns throwable.localizedMessage!!

        runTest(dispatcher) {
            val result = useCase(userID, chatID).swap().getOrNull()

            result shouldBeEqualTo throwable.localizedMessage
        }
    }
}