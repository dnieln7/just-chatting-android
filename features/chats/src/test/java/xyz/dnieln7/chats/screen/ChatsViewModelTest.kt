package xyz.dnieln7.chats.screen

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldContainSame
import org.junit.Test
import xyz.dnieln7.domain.usecase.GetChatsUseCase
import xyz.dnieln7.testing.fake.buildChats
import xyz.dnieln7.testing.relaxedMockk

class ChatsViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private val getChatsUseCase = relaxedMockk<GetChatsUseCase>()

    private lateinit var viewModel: ChatsViewModel

    @Test
    fun `GIVEN the happy path WHEN init THEN emit the expected states`() {
        val chats = buildChats()

        coEvery { getChatsUseCase() } returns chats.right()

        runTest(dispatcher) {
            viewModel = ChatsViewModel(dispatcher, getChatsUseCase)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo ChatsState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf ChatsState.Success::class
                    (it as ChatsState.Success).data shouldContainSame chats
                }
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN init THEN emit the expected states`() {
        val error = "Error"

        coEvery { getChatsUseCase() } returns error.left()

        runTest(dispatcher) {
            viewModel = ChatsViewModel(dispatcher, getChatsUseCase)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo ChatsState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf ChatsState.Error::class
                    (it as ChatsState.Error).message shouldBeEqualTo error
                }
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN re-init THEN emit the expected states`() {
        val chats = buildChats()

        coEvery { getChatsUseCase() } returns chats.right()

        viewModel = ChatsViewModel(dispatcher, getChatsUseCase)

        runTest(dispatcher) {
            viewModel.getChats()

            viewModel.state.test {
                skipItems(1)
                awaitItem() shouldBeEqualTo ChatsState.Loading
                awaitItem() shouldBeInstanceOf ChatsState.Success::class
            }
        }
    }
}