package xyz.dnieln7.friendships.screen

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.domain.usecase.GetUserByEmailUseCase
import xyz.dnieln7.domain.usecase.SendFriendshipRequestUseCase
import xyz.dnieln7.testing.fake.buildUser
import xyz.dnieln7.testing.relaxedMockk

@OptIn(ExperimentalCoroutinesApi::class)
class AddFriendshipViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private val getUserByEmailUseCase = relaxedMockk<GetUserByEmailUseCase>()
    private val sendFriendshipRequestUseCase = relaxedMockk<SendFriendshipRequestUseCase>()

    private lateinit var viewModel: AddFriendshipViewModel

    @Before
    fun setup() {
        viewModel = AddFriendshipViewModel(dispatcher, getUserByEmailUseCase, sendFriendshipRequestUseCase)
    }

    @Test
    fun `GIVEN the happy path WHEN nothing THEN initialize the expected state`() {
        viewModel.state.value shouldBeEqualTo AddFriendshipState.None
    }

    @Test
    fun `GIVEN the happy path WHEN getUserByEmail THEN emit the expected states`() {
        val email = "email@example.com"
        val user = buildUser()

        coEvery { getUserByEmailUseCase(email) } returns user.right()

        runTest(dispatcher) {
            viewModel.getUserByEmail(email)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo AddFriendshipState.None
                awaitItem() shouldBeEqualTo AddFriendshipState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf AddFriendshipState.UserFound::class
                    (it as AddFriendshipState.UserFound).user shouldBeEqualTo user
                }
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN getUserByEmail THEN emit the expected states`() {
        val email = "email@example.com"
        val error = "Error"

        coEvery { getUserByEmailUseCase(email) } returns error.left()

        runTest(dispatcher) {
            viewModel.getUserByEmail(email)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo AddFriendshipState.None
                awaitItem() shouldBeEqualTo AddFriendshipState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf AddFriendshipState.GetUserError::class
                    (it as AddFriendshipState.GetUserError).message shouldBeEqualTo error
                }
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN sendFriendshipRequest THEN emit the expected states`() {
        val user = buildUser()

        coEvery { sendFriendshipRequestUseCase(user.id) } returns Unit.right()

        runTest(dispatcher) {
            viewModel.sendFriendshipRequest(user)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo AddFriendshipState.None
                awaitItem() shouldBeEqualTo AddFriendshipState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf AddFriendshipState.FriendshipRequestSent::class
                    (it as AddFriendshipState.FriendshipRequestSent).user shouldBeEqualTo user
                }
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN sendFriendshipRequest THEN emit the expected states`() {
        val user = buildUser()
        val error = "Error"

        coEvery { sendFriendshipRequestUseCase(user.id) } returns error.left()

        runTest(dispatcher) {
            viewModel.sendFriendshipRequest(user)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo AddFriendshipState.None
                awaitItem() shouldBeEqualTo AddFriendshipState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf AddFriendshipState.SendFriendshipRequestError::class
                    (it as AddFriendshipState.SendFriendshipRequestError).message shouldBeEqualTo error
                    it.user shouldBeEqualTo user
                }
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN resetAddFriendshipState THEN emit the expected states`() {
        runTest(dispatcher) {
            viewModel.resetAddFriendshipState()

            viewModel.state.test {
                advanceUntilIdle()

                awaitItem() shouldBeEqualTo AddFriendshipState.None
            }
        }
    }
}