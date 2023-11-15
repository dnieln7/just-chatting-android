package xyz.dnieln7.login

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
import xyz.dnieln7.domain.usecase.LoginUseCase
import xyz.dnieln7.login.screen.LoginState
import xyz.dnieln7.login.screen.LoginViewModel
import xyz.dnieln7.testing.relaxedMockk

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private val loginUseCase = relaxedMockk<LoginUseCase>()

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        viewModel = LoginViewModel(dispatcher, loginUseCase)
    }

    @Test
    fun `GIVEN the happy path WHEN nothing THEN initialize the expected state`() {
        viewModel.state.value shouldBeEqualTo LoginState.None
    }

    @Test
    fun `GIVEN the happy path WHEN login THEN emit the expected states`() {
        val email = "email"
        val password = "password"

        coEvery { loginUseCase(email, password) } returns Unit.right()

        runTest(dispatcher) {
            viewModel.login(email, password)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo LoginState.None
                awaitItem() shouldBeEqualTo LoginState.Loading
                awaitItem() shouldBeEqualTo LoginState.Success
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN login THEN emit the expected states`() {
        val email = "email"
        val password = "password"
        val error = "error"

        coEvery { loginUseCase(email, password) } returns error.left()

        runTest(dispatcher) {
            viewModel.login(email, password)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo LoginState.None
                awaitItem() shouldBeEqualTo LoginState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf LoginState.Error::class
                    (it as LoginState.Error).message shouldBeEqualTo error
                }
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN onLoggedIn THEN emit the expected states`() {
        runTest(dispatcher) {
            viewModel.onLoggedIn()

            viewModel.state.test {
                advanceUntilIdle()

                awaitItem() shouldBeEqualTo LoginState.None
            }
        }
    }
}