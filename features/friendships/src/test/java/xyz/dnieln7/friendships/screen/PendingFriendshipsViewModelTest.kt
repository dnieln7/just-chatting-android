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
import xyz.dnieln7.domain.usecase.AcceptFriendshipRequestUseCase
import xyz.dnieln7.domain.usecase.DeleteFriendshipUseCase
import xyz.dnieln7.domain.usecase.GetPendingFriendshipsUseCase
import xyz.dnieln7.testing.fake.buildFriendships
import xyz.dnieln7.testing.relaxedMockk

class PendingFriendshipsViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    private val getPendingFriendshipsUseCase = relaxedMockk<GetPendingFriendshipsUseCase>()
    private val acceptFriendshipRequestUseCase = relaxedMockk<AcceptFriendshipRequestUseCase>()
    private val deleteFriendshipUseCase = relaxedMockk<DeleteFriendshipUseCase>()

    private lateinit var viewModel: PendingFriendshipsViewModel

    @Test
    fun `GIVEN the happy path WHEN init THEN emit the expected states`() {
        val friendships = buildFriendships()

        coEvery { getPendingFriendshipsUseCase() } returns friendships.right()

        runTest(dispatcher) {
            viewModel = PendingFriendshipsViewModel(
                dispatcher = dispatcher,
                getPendingFriendshipsUseCase = getPendingFriendshipsUseCase,
                acceptFriendshipRequestUseCase = acceptFriendshipRequestUseCase,
                deleteFriendshipUseCase = deleteFriendshipUseCase,
            )

            viewModel.state.test {
                awaitItem() shouldBeEqualTo PendingFriendshipsState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf PendingFriendshipsState.Success::class
                    (it as PendingFriendshipsState.Success).data shouldContainSame friendships
                }
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN init THEN emit the expected states`() {
        val error = "Error"

        coEvery { getPendingFriendshipsUseCase() } returns error.left()

        runTest(dispatcher) {
            viewModel = PendingFriendshipsViewModel(
                dispatcher = dispatcher,
                getPendingFriendshipsUseCase = getPendingFriendshipsUseCase,
                acceptFriendshipRequestUseCase = acceptFriendshipRequestUseCase,
                deleteFriendshipUseCase = deleteFriendshipUseCase,
            )

            viewModel.state.test {
                awaitItem() shouldBeEqualTo PendingFriendshipsState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf PendingFriendshipsState.Error::class
                    (it as PendingFriendshipsState.Error).message shouldBeEqualTo error
                }
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN re-init THEN emit the expected states`() {
        val friendships = buildFriendships()

        coEvery { getPendingFriendshipsUseCase() } returns friendships.right()

        viewModel = PendingFriendshipsViewModel(
            dispatcher = dispatcher,
            getPendingFriendshipsUseCase = getPendingFriendshipsUseCase,
            acceptFriendshipRequestUseCase = acceptFriendshipRequestUseCase,
            deleteFriendshipUseCase = deleteFriendshipUseCase,
        )

        runTest(dispatcher) {
            viewModel.getPendingFriendships()

            viewModel.state.test {
                skipItems(1)
                awaitItem() shouldBeEqualTo PendingFriendshipsState.Loading
                awaitItem() shouldBeInstanceOf PendingFriendshipsState.Success::class
            }
        }
    }
}