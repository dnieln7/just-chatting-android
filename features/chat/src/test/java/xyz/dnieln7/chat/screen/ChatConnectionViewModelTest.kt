package xyz.dnieln7.chat.screen

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContainAll
import org.amshove.kluent.shouldHaveSize
import org.amshove.kluent.shouldNotBeNull
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.chat.fake.FakeSingleChatConnection
import xyz.dnieln7.chat.navigation.CHAT_CHAT_ID
import xyz.dnieln7.chat.navigation.CHAT_USER_ID
import xyz.dnieln7.domain.usecase.GetMessagesUseCase
import xyz.dnieln7.testing.fake.buildChat
import xyz.dnieln7.testing.fake.buildMessages
import xyz.dnieln7.testing.relaxedMockk

@OptIn(ExperimentalCoroutinesApi::class)
class ChatConnectionViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private val savedStateHandle = relaxedMockk<SavedStateHandle>()
    private val getMessagesUseCase = relaxedMockk<GetMessagesUseCase>()
    private val singleChatConnection = FakeSingleChatConnection(dispatcher)

    private val chat = buildChat()
    private val chatID = chat.id
    private val userID = chat.me.id

    private lateinit var viewModel: ChatConnectionViewModel

    @Before
    fun setup() {
        every { savedStateHandle.get<String>(CHAT_CHAT_ID) } returns chatID
        every { savedStateHandle.get<String>(CHAT_USER_ID) } returns userID
    }

    @Test
    fun `GIVEN the happy path WHEN init THEN emit the expected states`() {
        val messages = buildMessages()

        coEvery { getMessagesUseCase(chatID) } returns messages.right()

        singleChatConnection.setHappyPath(true)

        runTest(dispatcher) {
            viewModel = ChatConnectionViewModel(dispatcher, savedStateHandle, getMessagesUseCase, singleChatConnection)

            viewModel.state.test {
                awaitItem().let {
                    it.status shouldBeEqualTo ChatConnectionStatus.NONE
                    it.messages.shouldBeEmpty()
                }
                awaitItem().let {
                    it.status shouldBeEqualTo ChatConnectionStatus.FETCHING_MESSAGES
                    it.messages.shouldBeEmpty()
                }
                awaitItem().let {
                    it.status shouldBeEqualTo ChatConnectionStatus.FETCHING_MESSAGES
                    it.messages shouldContainAll messages
                }
                awaitItem().let {
                    it.status shouldBeEqualTo ChatConnectionStatus.CONNECTING
                    it.messages shouldContainAll messages
                }
                awaitItem().let {
                    it.status shouldBeEqualTo ChatConnectionStatus.CONNECTED
                    it.messages shouldContainAll messages
                }
            }
        }
    }

    @Test
    fun `GIVEN an error from getMessagesUseCase WHEN init THEN emit the expected states`() {
        val error = "Error"

        coEvery { getMessagesUseCase(chatID) } returns error.left()

        singleChatConnection.setHappyPath(true)

        runTest(dispatcher) {
            viewModel = ChatConnectionViewModel(dispatcher, savedStateHandle, getMessagesUseCase, singleChatConnection)

            viewModel.state.test {
                awaitItem().let {
                    it.status shouldBeEqualTo ChatConnectionStatus.NONE
                    it.messages.shouldBeEmpty()
                }
                awaitItem().let {
                    it.status shouldBeEqualTo ChatConnectionStatus.FETCHING_MESSAGES
                    it.messages.shouldBeEmpty()
                }
                awaitItem().let {
                    it.status shouldBeEqualTo ChatConnectionStatus.MESSAGES_ERROR
                    it.messages.shouldBeEmpty()
                }
            }
        }
    }

    @Test
    fun `GIVEN an error from singleChatConnection WHEN init THEN emit the expected states`() {
        val messages = buildMessages()

        coEvery { getMessagesUseCase(chatID) } returns messages.right()

        singleChatConnection.setHappyPath(false)

        runTest(dispatcher) {
            viewModel = ChatConnectionViewModel(dispatcher, savedStateHandle, getMessagesUseCase, singleChatConnection)

            viewModel.state.test {
                awaitItem().let {
                    it.status shouldBeEqualTo ChatConnectionStatus.NONE
                    it.messages.shouldBeEmpty()
                }
                awaitItem().let {
                    it.status shouldBeEqualTo ChatConnectionStatus.FETCHING_MESSAGES
                    it.messages.shouldBeEmpty()
                }
                awaitItem().let {
                    it.status shouldBeEqualTo ChatConnectionStatus.FETCHING_MESSAGES
                    it.messages shouldContainAll messages
                }
                awaitItem().let {
                    it.status shouldBeEqualTo ChatConnectionStatus.CONNECTING
                    it.messages shouldContainAll messages
                }
                awaitItem().let {
                    it.status shouldBeEqualTo ChatConnectionStatus.CONNECTION_ERROR
                    it.messages shouldContainAll messages
                }
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN connect THEN emit the expected states`() {
        val messages = buildMessages()

        coEvery { getMessagesUseCase(chatID) } returns messages.right()

        singleChatConnection.setHappyPath(true)

        viewModel = ChatConnectionViewModel(dispatcher, savedStateHandle, getMessagesUseCase, singleChatConnection)

        runTest(dispatcher) {
            viewModel.connect()

            viewModel.state.test {
                skipItems(1)
                awaitItem().let {
                    it.status shouldBeEqualTo ChatConnectionStatus.CONNECTING
                    it.messages shouldContainAll messages
                }
                awaitItem().let {
                    it.status shouldBeEqualTo ChatConnectionStatus.CONNECTED
                    it.messages shouldContainAll messages
                }
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN getMessages THEN emit the expected states`() {
        val messages = buildMessages()

        coEvery { getMessagesUseCase(chatID) } returns messages.right()

        singleChatConnection.setHappyPath(true)

        viewModel = ChatConnectionViewModel(dispatcher, savedStateHandle, getMessagesUseCase, singleChatConnection)

        runTest(dispatcher) {
            viewModel.getMessages()

            viewModel.state.test {
                skipItems(1)
                awaitItem().let {
                    it.status shouldBeEqualTo ChatConnectionStatus.FETCHING_MESSAGES
                    it.messages.shouldBeEmpty()
                }
                awaitItem().let {
                    it.status shouldBeEqualTo ChatConnectionStatus.FETCHING_MESSAGES
                    it.messages shouldContainAll messages
                }
                awaitItem().let {
                    it.status shouldBeEqualTo ChatConnectionStatus.CONNECTING
                    it.messages shouldContainAll messages
                }
                awaitItem().let {
                    it.status shouldBeEqualTo ChatConnectionStatus.CONNECTED
                    it.messages shouldContainAll messages
                }
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN sendMessage THEN emit the expected states`() {
        val messages = buildMessages()
        val newMessageText = "NEW MESSAGE STRING"

        coEvery { getMessagesUseCase(chatID) } returns messages.right()

        singleChatConnection.setHappyPath(true)

        viewModel = ChatConnectionViewModel(dispatcher, savedStateHandle, getMessagesUseCase, singleChatConnection)

        runTest(dispatcher) {
            advanceUntilIdle()

            viewModel.sendMessage(newMessageText)

            viewModel.state.test {
                skipItems(1)
                awaitItem().let {
                    it.messages shouldHaveSize messages.size.plus(1)

                    val result = it.messages.first()

                    result.shouldNotBeNull()
                    result.message shouldBeEqualTo newMessageText
                }
            }
        }
    }
}