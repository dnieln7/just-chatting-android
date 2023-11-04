package xyz.dnieln7.friendships.screen

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
import xyz.dnieln7.domain.usecase.DeleteFriendshipUseCase
import xyz.dnieln7.domain.usecase.GetFriendshipsUseCase
import xyz.dnieln7.testing.fake.buildFriendships
import xyz.dnieln7.testing.relaxedMockk

class FriendshipsViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private val getFriendshipsUseCase = relaxedMockk<GetFriendshipsUseCase>()
    private val deleteFriendshipUseCase = relaxedMockk<DeleteFriendshipUseCase>()

    private lateinit var viewModel: FriendshipsViewModel

    @Test
    fun `GIVEN the happy path WHEN init THEN emit the expected states`() {
        val friendships = buildFriendships()

        coEvery { getFriendshipsUseCase() } returns friendships.right()

        runTest(dispatcher) {
            viewModel = FriendshipsViewModel(dispatcher, getFriendshipsUseCase, deleteFriendshipUseCase)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo FriendshipsState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf FriendshipsState.Success::class
                    (it as FriendshipsState.Success).data shouldContainSame friendships
                }
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN init THEN emit the expected states`() {
        val error = "Error"

        coEvery { getFriendshipsUseCase() } returns error.left()

        runTest(dispatcher) {
            viewModel = FriendshipsViewModel(dispatcher, getFriendshipsUseCase, deleteFriendshipUseCase)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo FriendshipsState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf FriendshipsState.Error::class
                    (it as FriendshipsState.Error).message shouldBeEqualTo error
                }
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN re-init THEN emit the expected states`() {
        val friendships = buildFriendships()

        coEvery { getFriendshipsUseCase() } returns friendships.right()

        viewModel = FriendshipsViewModel(dispatcher, getFriendshipsUseCase, deleteFriendshipUseCase)

        runTest(dispatcher) {
            viewModel.getFriendships()

            viewModel.state.test {
                skipItems(1)
                awaitItem() shouldBeEqualTo FriendshipsState.Loading
                awaitItem() shouldBeInstanceOf FriendshipsState.Success::class
            }
        }
    }
}