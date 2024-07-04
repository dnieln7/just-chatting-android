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
import xyz.dnieln7.domain.repository.MessageRepository
import xyz.dnieln7.testing.fake.buildException
import xyz.dnieln7.testing.relaxedMockk

class SendMessageUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val messageRepository = relaxedMockk<MessageRepository>()
    private val getErrorFromThrowableUseCase = relaxedMockk<GetErrorFromThrowableUseCase>()

    private lateinit var useCase: SendMessageUseCase

    @Before
    fun setup() {
        useCase = SendMessageUseCase(
            messageRepository = messageRepository,
            getErrorFromThrowableUseCase = getErrorFromThrowableUseCase,
        )
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return the expected result`() {
        val chatID = "chatID"
        val userID = "userID"
        val message = "message"

        coEvery { messageRepository.sendMessage(chatID, userID, message) } returns Unit.right()

        runTest(dispatcher) {
            val result = useCase(chatID, userID, message).getOrNull()

            result shouldBeEqualTo Unit
        }
    }

    @Test
    fun `GIVEN the unhappy WHEN invoke THEN return the expected result`() {
        val chatID = "chatID"
        val userID = "userID"
        val message = "message"
        val throwable = buildException()

        coEvery { messageRepository.sendMessage(chatID, userID, message) } returns throwable.left()
        every { getErrorFromThrowableUseCase(throwable) } returns throwable.localizedMessage!!

        runTest(dispatcher) {
            val result = useCase(chatID, userID, message).swap().getOrNull()

            result shouldBeEqualTo throwable.localizedMessage
        }
    }
}