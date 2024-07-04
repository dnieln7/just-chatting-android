package xyz.dnieln7.friendships.screen.friendships

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldContainSame
import org.amshove.kluent.shouldNotContain
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
        val statefulFriendships = friendships.map { StatefulFriendship(data = it) }

        coEvery { getFriendshipsUseCase() } returns friendships.right()

        runTest(dispatcher) {
            viewModel = FriendshipsViewModel(dispatcher, getFriendshipsUseCase, deleteFriendshipUseCase)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo FriendshipsState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf FriendshipsState.Success::class
                    (it as FriendshipsState.Success).data shouldContainSame statefulFriendships
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

    @Test
    fun `GIVEN the happy path WHEN deleteFriendship THEN emit the expected states`() {
        val friendships = buildFriendships()
        val friendship = friendships.first()

        coEvery { getFriendshipsUseCase() } returns friendships.right()
        coEvery { deleteFriendshipUseCase(friendship.id) } returns Unit.right()

        viewModel = FriendshipsViewModel(dispatcher, getFriendshipsUseCase, deleteFriendshipUseCase)

        runTest(dispatcher) {
            viewModel.deleteFriendship(friendship)

            viewModel.state.test {
                skipItems(1)
                awaitItem().let { state ->
                    state shouldBeInstanceOf FriendshipsState.Success::class
                    (state as FriendshipsState.Success).data.find { it.data.id == friendship.id }!!.isLoading.shouldBeTrue()
                }
                awaitItem().let {
                    it shouldBeInstanceOf FriendshipsState.Success::class
                    (it as FriendshipsState.Success).data shouldNotContain friendship
                }
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN deleteFriendship THEN emit the expected states`() {
        val friendships = buildFriendships()
        val friendship = friendships.first()
        val error = "Error"

        coEvery { getFriendshipsUseCase() } returns friendships.right()
        coEvery { deleteFriendshipUseCase(friendship.id) } returns error.left()

        viewModel = FriendshipsViewModel(dispatcher, getFriendshipsUseCase, deleteFriendshipUseCase)

        runTest(dispatcher) {
            viewModel.deleteFriendship(friendship)

            viewModel.state.test {
                skipItems(1)
                awaitItem().let { state ->
                    state shouldBeInstanceOf FriendshipsState.Success::class
                    (state as FriendshipsState.Success).data.find { it.data.id == friendship.id }!!.isLoading.shouldBeTrue()
                }
                awaitItem().let { state ->
                    state shouldBeInstanceOf FriendshipsState.Success::class
                    (state as FriendshipsState.Success).data.find { it.data.id == friendship.id }!!.isLoading.shouldBeFalse()
                }
            }
        }
    }
}