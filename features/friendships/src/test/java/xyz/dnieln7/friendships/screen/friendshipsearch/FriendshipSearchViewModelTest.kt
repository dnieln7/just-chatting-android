package xyz.dnieln7.friendships.screen.friendshipsearch

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
class FriendshipSearchViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private val getUserByEmailUseCase = relaxedMockk<GetUserByEmailUseCase>()
    private val sendFriendshipRequestUseCase = relaxedMockk<SendFriendshipRequestUseCase>()

    private lateinit var viewModel: FriendshipSearchViewModel

    @Before
    fun setup() {
        viewModel = FriendshipSearchViewModel(dispatcher, getUserByEmailUseCase, sendFriendshipRequestUseCase)
    }

    @Test
    fun `GIVEN the happy path WHEN nothing THEN initialize the expected state`() {
        viewModel.state.value shouldBeEqualTo FriendshipSearchState.None
    }

    @Test
    fun `GIVEN the happy path WHEN getUserByEmail THEN emit the expected states`() {
        val email = "email@example.com"
        val user = buildUser()

        coEvery { getUserByEmailUseCase(email) } returns user.right()

        runTest(dispatcher) {
            viewModel.getUserByEmail(email)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo FriendshipSearchState.None
                awaitItem() shouldBeEqualTo FriendshipSearchState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf FriendshipSearchState.UserFound::class
                    (it as FriendshipSearchState.UserFound).user shouldBeEqualTo user
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
                awaitItem() shouldBeEqualTo FriendshipSearchState.None
                awaitItem() shouldBeEqualTo FriendshipSearchState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf FriendshipSearchState.GetUserError::class
                    (it as FriendshipSearchState.GetUserError).message shouldBeEqualTo error
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
                awaitItem() shouldBeEqualTo FriendshipSearchState.None
                awaitItem() shouldBeEqualTo FriendshipSearchState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf FriendshipSearchState.FriendshipRequestSentSearch::class
                    (it as FriendshipSearchState.FriendshipRequestSentSearch).user shouldBeEqualTo user
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
                awaitItem() shouldBeEqualTo FriendshipSearchState.None
                awaitItem() shouldBeEqualTo FriendshipSearchState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf FriendshipSearchState.SendFriendshipSearchRequestError::class
                    (it as FriendshipSearchState.SendFriendshipSearchRequestError).message shouldBeEqualTo error
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

                awaitItem() shouldBeEqualTo FriendshipSearchState.None
            }
        }
    }
}