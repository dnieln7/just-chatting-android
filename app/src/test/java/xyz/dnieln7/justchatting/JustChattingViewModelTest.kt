package xyz.dnieln7.justchatting

import app.cash.turbine.test
import arrow.core.none
import arrow.core.some
import io.mockk.coEvery
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import xyz.dnieln7.justchatting.domain.usecase.GetUserUserCase
import xyz.dnieln7.justchatting.fake.buildUser
import xyz.dnieln7.justchatting.mockk.relaxedMockk

class JustChattingViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private val getUserUserCase = relaxedMockk<GetUserUserCase>()

    @Test
    fun `GIVEN the happy path WHEN init THEN emit the expected states`() {
        coEvery { getUserUserCase() } returns buildUser().some()

        runTest(dispatcher) {
            val viewModel = JustChattingViewModel(dispatcher, getUserUserCase)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo JustChattingViewModel.InitializedState.Loading
                awaitItem() shouldBeEqualTo JustChattingViewModel.InitializedState.Authenticated
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN login THEN emit the expected states`() {
        coEvery { getUserUserCase() } returns none()

        runTest(dispatcher) {
            val viewModel = JustChattingViewModel(dispatcher, getUserUserCase)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo JustChattingViewModel.InitializedState.Loading
                awaitItem() shouldBeEqualTo JustChattingViewModel.InitializedState.NotAuthenticated
            }
        }
    }
}