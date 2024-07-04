package xyz.dnieln7.friendships.screen

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldBeTrue
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.domain.usecase.CreateChatUseCase
import xyz.dnieln7.testing.fake.buildChat
import xyz.dnieln7.testing.fake.buildFriendship
import xyz.dnieln7.testing.relaxedMockk

class FriendshipsContainerViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private val createChatUseCase = relaxedMockk<CreateChatUseCase>()

    private lateinit var viewModel: FriendshipsContainerViewModel

    @Before
    fun setup() {
        viewModel = FriendshipsContainerViewModel(dispatcher, createChatUseCase)
    }

    @Test
    fun `GIVEN the happy path WHEN nothing THEN initialize the expected state`() {
        viewModel.state.value.let {
            it.currentScreen shouldBeEqualTo FriendshipScreen.FRIENDSHIPS
            it.showBottomSheet.shouldBeFalse()
            it.creatingChat.shouldBeFalse()
            it.createChatError.shouldBeNull()
            it.chat.shouldBeNull()
        }
    }

    @Test
    fun `GIVEN the happy path WHEN toggleBottomSheet THEN emit the expected states`() {
        runTest(dispatcher) {
            viewModel.toggleBottomSheet(true)

            viewModel.state.test {
                skipItems(1)
                awaitItem().showBottomSheet.shouldBeTrue()
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN toggleScreen THEN emit the expected state`() {
        runTest(dispatcher) {
            viewModel.toggleScreen(FriendshipScreen.PENDING_FRIENDSHIPS)

            viewModel.state.test {
                skipItems(1)
                awaitItem().currentScreen shouldBeEqualTo FriendshipScreen.PENDING_FRIENDSHIPS
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN createChat THEN emit the expected state`() {
        val friendship = buildFriendship()
        val chat = buildChat()

        coEvery { createChatUseCase(friendship.id) } returns chat.right()

        runTest(dispatcher) {
            viewModel.createChat(friendship)

            viewModel.state.test {
                skipItems(1)
                awaitItem().let {
                    it.creatingChat.shouldBeTrue()
                    it.createChatError.shouldBeNull()
                    it.chat.shouldBeNull()
                }
                awaitItem().let {
                    it.creatingChat.shouldBeFalse()
                    it.createChatError.shouldBeNull()
                    it.chat shouldBeEqualTo chat
                }
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN createChat THEN emit the expected state`() {
        val friendship = buildFriendship()
        val error = "Error"

        coEvery { createChatUseCase(friendship.id) } returns error.left()

        runTest(dispatcher) {
            viewModel.createChat(friendship)

            viewModel.state.test {
                skipItems(1)
                awaitItem().let {
                    it.creatingChat.shouldBeTrue()
                    it.createChatError.shouldBeNull()
                    it.chat.shouldBeNull()
                }
                awaitItem().let {
                    it.creatingChat.shouldBeFalse()
                    it.createChatError shouldBeEqualTo error
                    it.chat.shouldBeNull()
                }
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN resetChatState THEN emit the expected state`() {
        val friendship = buildFriendship()
        val error = "Error"

        coEvery { createChatUseCase(friendship.id) } returns error.left()

        viewModel.createChat(friendship)

        runTest(dispatcher) {
            viewModel.resetState()

            viewModel.state.test {
                skipItems(1)
                awaitItem().let {
                    it.creatingChat.shouldBeFalse()
                    it.createChatError.shouldBeNull()
                    it.chat.shouldBeNull()
                }
            }
        }
    }
}