package xyz.dnieln7.chat.screen

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldBeTrue
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.chat.navigation.CHAT_CHAT_ID
import xyz.dnieln7.chat.navigation.CHAT_USER_ID
import xyz.dnieln7.domain.usecase.GetChatUseCase
import xyz.dnieln7.testing.fake.buildChat
import xyz.dnieln7.testing.relaxedMockk

class ChatViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private val savedStateHandle = relaxedMockk<SavedStateHandle>()
    private val getChatUseCase = relaxedMockk<GetChatUseCase>()

    private val chat = buildChat()
    private val chatID = chat.id
    private val userID = chat.me.id
    private val friendID = chat.friend.id

    private lateinit var viewModel: ChatViewModel

    @Before
    fun setup() {
        every { savedStateHandle.get<String>(CHAT_CHAT_ID) } returns chatID
        every { savedStateHandle.get<String>(CHAT_USER_ID) } returns userID
    }

    @Test
    fun `GIVEN the happy path WHEN init THEN emit the expected states`() {
        coEvery { getChatUseCase(userID, chatID) } returns chat.right()

        runTest(dispatcher) {
            viewModel = ChatViewModel(dispatcher, savedStateHandle, getChatUseCase)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo ChatState.None
                awaitItem() shouldBeEqualTo ChatState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf ChatState.Success::class
                    (it as ChatState.Success).data shouldBeEqualTo chat
                }
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN init THEN emit the expected states`() {
        val error = "Error"

        coEvery { getChatUseCase(userID, chatID) } returns error.left()

        runTest(dispatcher) {
            viewModel = ChatViewModel(dispatcher, savedStateHandle, getChatUseCase)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo ChatState.None
                awaitItem() shouldBeEqualTo ChatState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf ChatState.Error::class
                    (it as ChatState.Error).message shouldBeEqualTo error
                }
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN re-init THEN emit the expected states`() {
        coEvery { getChatUseCase(userID, chatID) } returns chat.right()

        viewModel = ChatViewModel(dispatcher, savedStateHandle, getChatUseCase)

        runTest(dispatcher) {
            viewModel.getChat()

            viewModel.state.test {
                skipItems(1)
                awaitItem() shouldBeEqualTo ChatState.Loading
                awaitItem() shouldBeInstanceOf ChatState.Success::class
            }
        }
    }

    @Test
    fun `GIVEN my own userID WHEN isMe THEN return true`() {
        coEvery { getChatUseCase(userID, chatID) } returns chat.right()

        viewModel = ChatViewModel(dispatcher, savedStateHandle, getChatUseCase)

        runTest(dispatcher) {
            val result = viewModel.isMe(userID)

            result.shouldBeTrue()
        }
    }

    @Test
    fun `GIVEN not my own userID WHEN isMe THEN return false`() {
        coEvery { getChatUseCase(userID, chatID) } returns chat.right()

        viewModel = ChatViewModel(dispatcher, savedStateHandle, getChatUseCase)

        runTest(dispatcher) {
            val result = viewModel.isMe(friendID)

            result.shouldBeFalse()
        }
    }

    @Test
    fun `GIVEN my own userID WHEN getUsername THEN return the expected result`() {
        coEvery { getChatUseCase(userID, chatID) } returns chat.right()

        viewModel = ChatViewModel(dispatcher, savedStateHandle, getChatUseCase)

        runTest(dispatcher) {
            val result = viewModel.getUsername(userID)

            result shouldBeEqualTo chat.me.username
        }
    }

    @Test
    fun `GIVEN not my own userID WHEN getUsername THEN return the expected result`() {
        coEvery { getChatUseCase(userID, chatID) } returns chat.right()

        viewModel = ChatViewModel(dispatcher, savedStateHandle, getChatUseCase)

        runTest(dispatcher) {
            val result = viewModel.getUsername(friendID)

            result shouldBeEqualTo chat.friend.username
        }
    }
}