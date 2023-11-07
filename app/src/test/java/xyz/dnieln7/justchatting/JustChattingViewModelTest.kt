package xyz.dnieln7.justchatting

import app.cash.turbine.test
import arrow.core.none
import arrow.core.some
import io.mockk.coEvery
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import xyz.dnieln7.domain.usecase.GetUserUseCase
import xyz.dnieln7.testing.fake.buildUser
import xyz.dnieln7.testing.relaxedMockk

class JustChattingViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private val getUserUseCase = relaxedMockk<GetUserUseCase>()

    @Test
    fun `GIVEN the happy path WHEN init THEN emit the expected states`() {
        coEvery { getUserUseCase() } returns buildUser().some()

        runTest(dispatcher) {
            val viewModel = JustChattingViewModel(dispatcher, getUserUseCase)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo JustChattingViewModel.InitializedState.Loading
                awaitItem() shouldBeEqualTo JustChattingViewModel.InitializedState.Authenticated
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN login THEN emit the expected states`() {
        coEvery { getUserUseCase() } returns none()

        runTest(dispatcher) {
            val viewModel = JustChattingViewModel(dispatcher, getUserUseCase)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo JustChattingViewModel.InitializedState.Loading
                awaitItem() shouldBeEqualTo JustChattingViewModel.InitializedState.NotAuthenticated
            }
        }
    }
}