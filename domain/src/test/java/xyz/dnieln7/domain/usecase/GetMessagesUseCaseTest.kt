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
import xyz.dnieln7.domain.repository.MessageRepository
import xyz.dnieln7.testing.fake.buildException
import xyz.dnieln7.testing.fake.buildMessages
import xyz.dnieln7.testing.relaxedMockk

class GetMessagesUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val messageRepository = relaxedMockk<MessageRepository>()
    private val getErrorFromThrowableUseCase = relaxedMockk<GetErrorFromThrowableUseCase>()

    private lateinit var useCase: GetMessagesUseCase

    @Before
    fun setup() {
        useCase = GetMessagesUseCase(
            messageRepository = messageRepository,
            getErrorFromThrowableUseCase = getErrorFromThrowableUseCase,
        )
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return the expected result`() {
        val chatID = "chatID"
        val messages = buildMessages()

        coEvery { messageRepository.getMessages(chatID) } returns messages.right()

        runTest(dispatcher) {
            val result = useCase(chatID).getOrNull()

            result.shouldNotBeNull()
            result shouldContainSame messages
        }
    }

    @Test
    fun `GIVEN the unhappy WHEN invoke THEN return the expected result`() {
        val chatID = "chatID"
        val throwable = buildException()

        coEvery { messageRepository.getMessages(chatID) } returns throwable.left()
        every { getErrorFromThrowableUseCase(throwable) } returns throwable.localizedMessage!!

        runTest(dispatcher) {
            val result = useCase(chatID).swap().getOrNull()

            result shouldBeEqualTo throwable.localizedMessage
        }
    }
}