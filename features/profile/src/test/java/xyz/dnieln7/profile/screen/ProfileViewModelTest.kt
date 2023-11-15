package xyz.dnieln7.profile.screen

import app.cash.turbine.test
import arrow.core.left
import arrow.core.none
import arrow.core.right
import arrow.core.some
import io.mockk.coEvery
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test
import xyz.dnieln7.domain.usecase.GetUserUseCase
import xyz.dnieln7.domain.usecase.LogoutUseCase
import xyz.dnieln7.testing.fake.buildUser
import xyz.dnieln7.testing.relaxedMockk

class ProfileViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private val getUserUseCase = relaxedMockk<GetUserUseCase>()
    private val logoutUseCase = relaxedMockk<LogoutUseCase>()

    private lateinit var viewModel: ProfileViewModel

    @Test
    fun `GIVEN the happy path WHEN init THEN emit the expected states`() {
        val user = buildUser()

        coEvery { getUserUseCase() } returns user.some()

        runTest(dispatcher) {
            viewModel = ProfileViewModel(dispatcher, getUserUseCase, logoutUseCase)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo ProfileState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf ProfileState.UserFound::class
                    (it as ProfileState.UserFound).data shouldBeEqualTo user
                }
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN init THEN emit the expected states`() {
        coEvery { getUserUseCase() } returns none()

        runTest(dispatcher) {
            viewModel = ProfileViewModel(dispatcher, getUserUseCase, logoutUseCase)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo ProfileState.Loading
                awaitItem() shouldBeEqualTo ProfileState.UserNotFound
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN re-init THEN emit the expected states`() {
        val user = buildUser()

        coEvery { getUserUseCase() } returns user.some()

        viewModel = ProfileViewModel(dispatcher, getUserUseCase, logoutUseCase)

        runTest(dispatcher) {
            viewModel.getUser()

            viewModel.state.test {
                skipItems(1)
                awaitItem() shouldBeEqualTo ProfileState.Loading
                awaitItem() shouldBeInstanceOf ProfileState.UserFound::class
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN logout THEN emit the expected states`() {
        val user = buildUser()

        coEvery { logoutUseCase() } returns Unit.right()
        coEvery { getUserUseCase() } returns user.some()

        viewModel = ProfileViewModel(dispatcher, getUserUseCase, logoutUseCase)

        runTest(dispatcher) {
            viewModel.logout()

            viewModel.state.test {
                skipItems(1)
                awaitItem() shouldBeEqualTo ProfileState.Loading
                awaitItem() shouldBeInstanceOf ProfileState.LoggedOut::class
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN logout THEN emit the expected states`() {
        val user = buildUser()
        val error = "Error"

        coEvery { logoutUseCase() } returns error.left()
        coEvery { getUserUseCase() } returns user.some()

        viewModel = ProfileViewModel(dispatcher, getUserUseCase, logoutUseCase)

        runTest(dispatcher) {
            viewModel.logout()

            viewModel.state.test {
                skipItems(1)
                awaitItem() shouldBeEqualTo ProfileState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf ProfileState.LogoutError::class
                    (it as ProfileState.LogoutError).message shouldBeEqualTo error
                }
            }
        }
    }
}